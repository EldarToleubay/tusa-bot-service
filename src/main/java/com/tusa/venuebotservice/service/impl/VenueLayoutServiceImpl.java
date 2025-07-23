package com.tusa.venuebotservice.service.impl;

import com.tusa.venuebotservice.entity.Venue;
import com.tusa.venuebotservice.service.FileServiceClient;
import com.tusa.venuebotservice.service.VenueLayoutService;
import com.tusa.venuebotservice.service.VenueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class VenueLayoutServiceImpl implements VenueLayoutService {
    private final FileServiceClient fileServiceClient;
    private final VenueService venueService;
    private final MinioService minioService;

    @Override
    public ResponseEntity<String> savePng(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        log.info("Saving PNG file with id {}", file.getOriginalFilename());
        Venue venue = venueService.findVenueById(id);
        venue.setLayoutUrl(file.getOriginalFilename());
        venueService.updateVenue(id, venue);
        minioService.uploadFile(file.getOriginalFilename(), file);
        String fileName = String.valueOf(fileServiceClient.uploadPng(file));
        return ResponseEntity.ok(fileName);
    }


}
