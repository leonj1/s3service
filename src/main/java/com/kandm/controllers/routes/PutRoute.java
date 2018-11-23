package com.kandm.controllers.routes;

import com.kandm.services.S3Service;
import com.kandm.services.SimpleExitRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.utils.StringUtils;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class PutRoute implements Route {
    private static final Logger log = LoggerFactory.getLogger(PutRoute.class);
    private final S3Service s3Service;

    public PutRoute(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    private Object execute(Response res, byte[] contents, String site, String path, String fileName) {
        try {
            this.s3Service.uploadS3Object(
                    String.format(
                            "%s/%s/%s",
                            site,
                            path,
                            fileName
                    ),
                    contents
            );
            return SimpleExitRoute.builder(res).OK_200().text("uploaded");
        } catch (Exception e) {
            String msg = String.format(
                    "Problem uploading %s/%s",
                    site,
                    path
            );
            log.error(msg);
            e.printStackTrace();
            return SimpleExitRoute.builder(res)
                    .INTERNAL_SERVER_ERROR_500()
                    .text(e.getMessage());
        }
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String site = request.queryParams("site");
        String path = request.queryParams("path");
        String fileName = request.queryParams("file");
        if(StringUtils.isEmpty(site) || StringUtils.isEmpty(path) || StringUtils.isEmpty(fileName)) {
            return SimpleExitRoute.builder(response)
                    .BAD_REQUEST_400()
                    .text("token, site, path, and file need to be provided as query params");
        }
        return execute(response, request.bodyAsBytes(), site, path, fileName);
    }
}
