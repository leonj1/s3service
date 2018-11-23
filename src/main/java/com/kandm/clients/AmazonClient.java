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
    private String bucketName;

    public AmazonClient(AmazonS3 conn, String bucketName) {
        this.conn = conn;
        this.bucketName = bucketName;
    }

    @Override
    public byte[] getObject(String key) throws Exception {
        log.info(String.format(
                "Attempting to fetch bucket %s key %s",
                this.bucketName,
                key
        ));
        return IOUtils.readFully(
                conn.getObject(
                        new GetObjectRequest(
                                this.bucketName,
                                key
                        )
                ).getObjectContent(),
                -1,
                false
        );
    }

    @Override
    public void putObject(String key, File outputFile) throws Exception {
        log.info(String.format(
                "Attempting to put bucket %s key %s",
                this.bucketName,
                key
        ));
        conn.putObject(new PutObjectRequest(this.bucketName, key, outputFile));
    }

    @Override
    public boolean exists(String key) throws Exception {
        return conn.doesObjectExist(this.bucketName, key);
    }

    @Override
    public void deleteObject(String key) throws Exception {
        if(!exists(key)) {
            log.info(String.format(
                    "Does not exist: bucket %s key %s",
                    this.bucketName,
                    key
            ));
            return;
        }
        log.info(String.format(
                "Deleting bucket %s key %s",
                this.bucketName,
                key
        ));
        conn.deleteObject(bucketName, key);
    }
}
