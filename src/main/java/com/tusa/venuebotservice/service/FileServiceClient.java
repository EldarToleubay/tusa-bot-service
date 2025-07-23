package com.tusa.venuebotservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
        name = "file-service",
        url = "http://localhost:8082/api/files/"
)
public interface FileServiceClient {
    @PostMapping("/upload")
    String save(@RequestParam("file") MultipartFile file);

    @GetMapping("/download/{filename:.+}")
    ResponseEntity<Resource> download(@PathVariable String filename);

    @PostMapping(value = "/upload-png", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadPng( @RequestPart("file") MultipartFile file);
}
