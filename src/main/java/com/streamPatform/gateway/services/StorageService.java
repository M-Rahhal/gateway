package com.streamPatform.gateway.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
@Service
public class StorageService {

    @Value("${app.storage.filtered}")
    private String filteredPath;

    @Value("${app.storage.unfiltered}")
    private String unfilteredPath;


    public String saveFilteredVideo(MultipartFile video, String videoName) throws Exception {
        if (video.isEmpty())
            throw new Exception("The video is empty!");

        byte[] videoBytes = video.getBytes();
        Path path = Paths.get(filteredPath+"\\"+videoName);
        Files.write(path , videoBytes);

        return path.toString();
    }

    public String saveUnfilteredVideo(MultipartFile video, String videoName) throws Exception {
        if (video.isEmpty())
            throw new Exception("The video is empty!");

        byte[] videoBytes = video.getBytes();
        Path path = Paths.get(unfilteredPath+"\\"+videoName);
        Files.write(path , videoBytes);

        return path.toString();
    }

}