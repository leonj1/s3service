package com.kandm.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kandm.exceptions.ErrorCreatingTempFile;
import sun.misc.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2016
 **/
public class S3Service {
    private AmazonS3 conn;

    public S3Service(AmazonS3 conn) {
        this.conn = conn;
    }

    /**
     * Read a binary file from S3
     */
    public byte[] readS3ObjectUsingByteArray(String bucketName, String key) throws IOException {
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

    public void uploadS3Object(String bucketName, String key, byte[] contents) throws ErrorCreatingTempFile, IOException {
        File outputFile = File.createTempFile(key, ".png");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile) ) {
            outputStream.write(contents);  //write the bytes and your done.
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorCreatingTempFile(e.getMessage());
        }
        conn.putObject(new PutObjectRequest(bucketName, key, outputFile));
    }
}
