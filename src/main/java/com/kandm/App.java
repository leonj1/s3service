package com.kandm;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.josemleon.CommandlineParser;
import com.josemleon.GetEffectiveProperty;
import com.josemleon.GetProperty;
import com.josemleon.Parser;
import com.kandm.clients.AmazonClient;
import com.kandm.clients.MyMinioClient;
import com.kandm.clients.S3Client;
import com.kandm.config.AppProperties;
import com.kandm.controllers.Controller;
import com.kandm.controllers.HealthCheckController;
import com.kandm.controllers.RestEndpoints;
import com.kandm.controllers.S3Controller;
import com.kandm.controllers.filters.BeforeFilter;
import com.kandm.controllers.filters.SparkFilter;
import com.kandm.controllers.filters.WebServerFilters;
import com.kandm.controllers.routes.GetRoute;
import com.kandm.controllers.routes.PutRoute;
import com.kandm.controllers.routes.SimpleHealthCheckRoute;
import com.kandm.services.S3Service;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.SparkBase.port;

/**
 * This service is meant to be the interface to Amazon S3
 * This is to prevent every micro service in dapidi-land from having to have the same code
 */
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    private static final String APPLICATION_PROPERTIES = "application.properties";

    public static void main( String[] args ) throws Exception {
        log.info("Some succeed because they are destined to. Most succeed because they are determined to. -- Unknown");

        // Starting here, we are going the job Spring would normally do
        AppProperties appProperties = null;
        Parser cmdlineParser = new CommandlineParser(args);
        try {
            appProperties = new AppProperties(
                    new GetEffectiveProperty(
                            new GetProperty(
                                    APPLICATION_PROPERTIES,
                                    cmdlineParser
                            ),
                            cmdlineParser
                    )
            );
        } catch (Exception e) {
            log.error(String.format("Really bad problem trying to find resource %s", APPLICATION_PROPERTIES));
            System.exit(1);
        }

        port(appProperties.httpPort());

        // Initialize Web server filters
        WebServerFilters webServerFilters = new WebServerFilters(
                new SparkFilter[]{
                        new BeforeFilter(appProperties.secretToken())
                }
        );
        webServerFilters.start();

        S3Client s3Client = null;
        if("prod".equals(appProperties.profile())) {
            s3Client = new AmazonClient(
                    new AmazonS3Client(
                            new BasicAWSCredentials(
                                    appProperties.getSesKey(),
                                    appProperties.getSesSecret()
                            )
                    )
            );
        } else {
            s3Client = new MyMinioClient(
                    new MinioClient(
                            appProperties.awsS3Endpoint(),
                            appProperties.getSesKey(),
                            appProperties.getSesSecret()
                    )
            );
        }

        S3Service s3Service = new S3Service(s3Client);

        RestEndpoints restEndpoints = new RestEndpoints(
                new Controller[]{
                        new S3Controller(
                                new GetRoute(s3Service),
                                new PutRoute(s3Service)
                        ),
                        new HealthCheckController(
                                new SimpleHealthCheckRoute()
                        )
                }
        );
        restEndpoints.start();

    }
}
