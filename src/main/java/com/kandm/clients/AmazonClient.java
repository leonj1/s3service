package com.kandm.clients;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import sun.misc.IOUtils;

import java.io.File;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class AmazonClient implements S3Client {
    private AmazonS3 conn;

    public AmazonClient(AmazonS3 conn) {
        this.conn = conn;
    }

    @Override
    public byte[] getObject(String bucketName, String key) throws Exception {
        return IOUtils.readFully(
                conn.getObject(
                        new GetObjectRequest(
                                bucketName,
                                key
                        )
                ).getObjectContent(),
                -1,
                false
        );
    }

    @Override
    public void putObject(String bucketName, String key, File outputFile) throws Exception {
        conn.putObject(new PutObjectRequest(bucketName, key, outputFile));
    }

    @Override
    public boolean exists(String bucketName, String key) throws Exception {
        return conn.doesObjectExist(bucketName, key);
    }

    @Override
    public void deleteObject(String bucketName, String key) throws Exception {
        if(!exists(bucketName, key)) {
            return;
        }
        conn.deleteObject(bucketName, key);
    }
}
