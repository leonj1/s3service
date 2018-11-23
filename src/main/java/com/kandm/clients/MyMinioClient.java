package com.kandm.clients;

import io.minio.MinioClient;
import sun.misc.IOUtils;

import java.io.File;
import java.io.InputStream;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class MyMinioClient implements S3Client {
    private MinioClient minioClient;
    private String bucketName;

    public MyMinioClient(MinioClient minioClient, String bucketName) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

    @Override
    public void deleteObject(String key) throws Exception {
        if(!exists(key)) {
            return;
        }
        minioClient.removeObject(this.bucketName, key);
    }

    @Override
    public boolean exists(String key) throws Exception {
        try {
            minioClient.statObject(this.bucketName, key);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public byte[] getObject(String key) throws Exception {
        InputStream stream = this.minioClient.getObject(
                this.bucketName,
                key
        );

        byte[] results = IOUtils.readFully(stream, -1, false);
        stream.close();
        return results;
    }

    @Override
    public void putObject(String key, File outputFile) throws Exception {
        this.minioClient.putObject(
                this.bucketName,
                key,
                outputFile.getPath()
        );
    }
}
