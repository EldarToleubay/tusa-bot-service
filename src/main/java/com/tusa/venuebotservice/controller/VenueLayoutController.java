package com.tusa.venuebotservice.controller;

import com.tusa.venuebotservice.service.VenueLayoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/layout-png")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class   VenueLayoutController {
    private final VenueLayoutService venueLayoutService;


    @PostMapping(value = "/upload-png/{id}",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload( @PathVariable Long id,@RequestPart("file") MultipartFile file) {
       return venueLayoutService.savePng(id,file);
    }

}
