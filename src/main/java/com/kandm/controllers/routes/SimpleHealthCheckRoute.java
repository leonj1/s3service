package com.kandm.controllers.routes;

import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class SimpleHealthCheckRoute implements Route {

    public SimpleHealthCheckRoute() {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        response.status(HttpStatus.OK_200);
        return "OK";
    }
}
