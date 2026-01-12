package com.project.roomie.ports.out;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BucketPortOut {

    String upload(MultipartFile file) throws IOException;
    void delete(String url) throws IOException;
}