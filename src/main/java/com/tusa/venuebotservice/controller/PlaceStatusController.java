package com.tusa.venuebotservice.controller;

import com.tusa.venuebotservice.dto.PlaceStatusDto;
import com.tusa.venuebotservice.service.PlaceStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/table-status")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class PlaceStatusController {
    private final PlaceStatusService placeStatusService;


    @GetMapping("/{venueId}/date")
    public List<PlaceStatusDto> getDeskAvailabilityF(@PathVariable Long venueId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return placeStatusService.getPlaceAvailability(venueId, date);
    }

    @PostMapping("/{venueId}/{placeId}/timeSlot")
    public List<PlaceStatusDto> setPlaceStatus(@PathVariable Long venueId, @PathVariable Long placeId,String timeSlot) {
        return placeStatusService.setPlaceStatus(venueId,placeId,timeSlot);
    }



}
