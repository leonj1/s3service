package com.kandm.controllers.filters;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2016
 **/
public interface SparkFilter {

    void init();

    public class Fake implements SparkFilter {

        @Override
        public void init() {
            return;
        }
    }
}
