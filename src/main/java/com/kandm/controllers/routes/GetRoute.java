package com.kandm.controllers.routes;

import com.google.common.net.MediaType;
import com.kandm.services.S3Service;
import com.kandm.services.SimpleExitRoute;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.utils.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class GetRoute implements Route {
    private static final Logger log = LoggerFactory.getLogger(GetRoute.class);
    private final S3Service s3Service;

    public GetRoute(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    private Object execute(Response res, String site, String path, String fileName) {
        try {
            byte[] file = this.s3Service.readS3ObjectUsingByteArray(
                    site,
                    String.format(
                            "%s/%s",
                            path,
                            fileName
                    )
            );

            res.header("Content-Type", "image/png");
            res.type(MediaType.ANY_IMAGE_TYPE.toString());
            res.raw().setContentLength(file.length);

            try {
                HttpServletResponse raw = res.raw();
                raw.getOutputStream().write(file);
                raw.getOutputStream().flush();
                raw.getOutputStream().close();
                res.status(HttpStatus.OK_200);
                return res.raw();
            } catch (IOException e) {
                res.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
                log.error(String.format("Problem creating generic resize of image for site %s, filename %s. Error: %s", site, fileName, e.getMessage()));
                return file;
            }

        } catch (Exception e) {
            String msg = String.format("Problem fetching %s/%s/%s. Error: %s", site, path, fileName, e.getMessage());
            log.error(msg);
            e.printStackTrace();
            return SimpleExitRoute.builder(res)
                    .INTERNAL_SERVER_ERROR_500()
                    .text(msg);
        }
    }

    @Override
    public Object handle(Request req, Response res) throws Exception {
        String site = req.queryParams("site");
        String path = req.queryParams("path");
        String file = req.queryParams("file");
        if(StringUtils.isEmpty(site) || StringUtils.isEmpty(path) || StringUtils.isEmpty(file)) {
            return SimpleExitRoute.builder(res)
                    .BAD_REQUEST_400()
                    .text("missing site, path, or file in query string");
        }
        return execute(res, site, path, file);
    }
}
