package com.kandm.clients;

import java.io.File;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public interface S3Client {
    byte[] getObject(String key) throws Exception;
    void putObject(String key, File outputFile) throws Exception;
    void deleteObject(String key) throws Exception;
    boolean exists(String key) throws Exception;
}
