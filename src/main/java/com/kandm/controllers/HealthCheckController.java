package com.kandm.controllers;


import com.kandm.controllers.routes.SimpleHealthCheckRoute;

import static spark.Spark.get;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class HealthCheckController implements Controller {

    private SimpleHealthCheckRoute healthCheckRoute;

    public HealthCheckController(SimpleHealthCheckRoute healthCheckRoute) {
        this.healthCheckRoute = healthCheckRoute;
    }

    public void expose() {
        get("/public/health", this.healthCheckRoute);
    }
}
