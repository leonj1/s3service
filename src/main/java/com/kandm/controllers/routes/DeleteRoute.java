package com.kandm.controllers.routes;

import com.kandm.services.S3Service;
import com.kandm.services.SimpleExitRoute;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.utils.StringUtils;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class DeleteRoute implements Route {
    private S3Service s3Service;

    public DeleteRoute(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    private String execute(Response res, String site, String path, String fileName) {
        try {
            this.s3Service.deleteS3Object(
                    String.format(
                            "%s/%s/%s",
                            site,
                            path,
                            fileName
                    )
            );
            return SimpleExitRoute.builder(res).OK_200().text("deleted");
        } catch (Exception e) {
            e.printStackTrace();
            return SimpleExitRoute.builder(res).INTERNAL_SERVER_ERROR_500().text(e.getMessage());
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
