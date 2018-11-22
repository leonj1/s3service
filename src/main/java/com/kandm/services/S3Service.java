package com.kandm.services;

import com.kandm.clients.S3Client;
import com.kandm.exceptions.ErrorCreatingTempFile;
import com.kandm.exceptions.ProblemWritingToS3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public void uploadS3Object(String bucketName, String key, byte[] contents) throws ErrorCreatingTempFile, IOException, ProblemWritingToS3 {
        File outputFile = File.createTempFile(key, "");
        Path p = Paths.get(outputFile.getPath());
        Files.write(p, contents);
        try {
            this.s3Client.putObject(bucketName, key, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProblemWritingToS3("problem writing to S3");
        }
    }

    public void deleteS3Object(String bucketName, String key) throws Exception {
        this.s3Client.deleteObject(bucketName, key);
    }
}
