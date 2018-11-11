package com.kandm.controllers.filters;


/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class WebServerFilters {
    private SparkFilter[] filters;

    public WebServerFilters(SparkFilter[] filters) {
        this.filters = filters;
    }

    public void start() {
        for(SparkFilter filter : this.filters) {
            filter.init();
        }
    }
}
