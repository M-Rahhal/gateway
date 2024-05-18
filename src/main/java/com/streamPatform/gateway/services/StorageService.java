package com.streamPatform.gateway.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.ByteBuffer;
import java.util.Base64;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
@Service
public class StorageService {

    protected S3Client s3;
    @Value("${app.awsServices.bucketName}")
    protected String bucketName;

    @Value("${app.awsServices.baseBucketUrl}")
    protected String baseBucketUrl;

    public String uploadFile(String base64String, String fileName) {
        Region region = Region.US_WEST_2;
        s3 = S3Client.builder()
                .region(region)
                .build();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3.putObject(objectRequest, RequestBody.fromByteBuffer(base64Decoder(base64String)));
        return baseBucketUrl + fileName;
    }


    protected ByteBuffer base64Decoder(String base64String) {
        return ByteBuffer.wrap(Base64.getDecoder().decode(base64String));
    }

}