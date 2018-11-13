package com.kandm.services;

import com.kandm.clients.S3Client;
import com.kandm.exceptions.ErrorCreatingTempFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2016
 **/
public class S3Service {
    private S3Client s3Client;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * Read a binary file from S3
     */
    public byte[] readS3ObjectUsingByteArray(String bucketName, String key) throws Exception {
        return this.s3Client.getObject(bucketName, key);
    }

    public void uploadS3Object(String bucketName, String key, byte[] contents) throws ErrorCreatingTempFile, IOException {
        File outputFile = File.createTempFile(key, ".png");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile) ) {
            outputStream.write(contents);  //write the bytes and your done.
            this.s3Client.putObject(bucketName, key, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorCreatingTempFile(e.getMessage());
        }
    }
}
