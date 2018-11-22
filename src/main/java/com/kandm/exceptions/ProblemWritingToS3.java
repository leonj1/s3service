package com.kandm.exceptions;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class ProblemWritingToS3 extends Exception {
    public ProblemWritingToS3(String message) {
        super(message);
    }
}
