package com.kandm.controllers;

import com.kandm.controllers.routes.DeleteRoute;
import com.kandm.controllers.routes.GetRoute;
import com.kandm.controllers.routes.PutRoute;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class S3Controller implements Controller {
    private GetRoute getRoute;
    private PutRoute putRoute;
    private DeleteRoute deleteRoute;

    public S3Controller(GetRoute getRoute, PutRoute putRoute, DeleteRoute deleteRoute) {
        this.getRoute = getRoute;
        this.putRoute = putRoute;
        this.deleteRoute = deleteRoute;
    }

    @Override
    public void expose() {
        get("/", this.getRoute);
        post("/", this.putRoute);
        delete("/", this.deleteRoute);
    }
}
