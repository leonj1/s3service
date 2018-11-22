package com.kandm.clients;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.IOUtils;

import java.io.File;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class AmazonClient implements S3Client {
    private static final Logger log = LoggerFactory.getLogger(S3Client.class);
    private AmazonS3 conn;

    public AmazonClient(AmazonS3 conn) {
        this.conn = conn;
    }

    @Override
    public byte[] getObject(String bucketName, String key) throws Exception {
        log.info(String.format(
                "Attempting to fetch bucket %s key %s",
                bucketName,
                key
        ));
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
        log.info(String.format(
                "Attempting to put bucket %s key %s",
                bucketName,
                key
        ));
        conn.putObject(new PutObjectRequest(bucketName, key, outputFile));
    }

    @Override
    public boolean exists(String bucketName, String key) throws Exception {
        return conn.doesObjectExist(bucketName, key);
    }

    @Override
    public void deleteObject(String bucketName, String key) throws Exception {
        if(!exists(bucketName, key)) {
            log.info(String.format(
                    "Does not exist: bucket %s key %s",
                    bucketName,
                    key
            ));
            return;
        }
        log.info(String.format(
                "Deleting bucket %s key %s",
                bucketName,
                key
        ));
        conn.deleteObject(bucketName, key);
    }
}
