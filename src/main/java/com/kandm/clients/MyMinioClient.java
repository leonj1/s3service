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

    public MyMinioClient(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public byte[] getObject(String bucketName, String key) throws Exception {
        InputStream stream = this.minioClient.getObject(
                bucketName,
                key
        );

        byte[] results = IOUtils.readFully(stream, -1, false);
        stream.close();
        return results;
    }

    @Override
    public void putObject(String bucketName, String key, File outputFile) throws Exception {
        this.minioClient.putObject(
                bucketName,
                key,
                outputFile.getPath()
        );
    }
}
