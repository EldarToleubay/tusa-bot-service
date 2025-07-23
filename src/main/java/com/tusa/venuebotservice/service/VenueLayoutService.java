package com.tusa.venuebotservice.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface VenueLayoutService {


    ResponseEntity<String> savePng(@PathVariable Long id, @RequestPart("file") MultipartFile file);
}
