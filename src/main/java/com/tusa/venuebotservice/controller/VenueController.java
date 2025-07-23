package com.tusa.venuebotservice.controller;

import com.tusa.venuebotservice.entity.Venue;
import com.tusa.venuebotservice.service.FileServiceClient;
import com.tusa.venuebotservice.service.VenueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class VenueController {
    private final VenueService venueService;
    private final FileServiceClient fileServiceClient;

    @PostMapping
    public Venue createVenue(Venue venue) {
        log.info("Venue created: {}:", venue);
        return venueService.createVenue(venue);
    }


    @GetMapping
    public List<Venue> getAllVenues() {
        return venueService.findAllVenues();
    }

    @DeleteMapping("/delete-venue/{id}")
    public void deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
    }

    @GetMapping("/find-venue/{id}")
    public Venue findVenueById(@PathVariable Long id) {
        return venueService.findVenueById(id);
    }

    @GetMapping("/find-venue-png/{id}")
    public ResponseEntity<InputStreamResource> download(@PathVariable Long id) {
        String fileName = venueService.findVenueById(id).getLayoutUrl();
        try {
            log.info("Downloading file: {}", fileName);
            InputStream inputStream = venueService.download(fileName);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }


    }

    @PutMapping("/update-venue/{id}")
    public Venue updateVenue(@PathVariable Long id, @RequestBody Venue venue) {
        return venueService.updateVenue(id, venue);
    }


}
