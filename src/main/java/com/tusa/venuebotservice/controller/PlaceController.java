package com.tusa.venuebotservice.controller;

import com.tusa.venuebotservice.dto.PlaceDto;
import com.tusa.venuebotservice.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping("/{venueId}")
    public PlaceDto addPlace(@PathVariable Long venueId,
                             @RequestBody PlaceDto placeDto) {
        return placeService.addPlace(venueId, placeDto);
    }

    @GetMapping
    public List<PlaceDto> getPlaceAll(@RequestParam Long venueId) {
        return placeService.getAllPlace(venueId);
    }

    @GetMapping("/{venueId}/{id}")
    public PlaceDto getPlace(@PathVariable Long venueId, @PathVariable Long id) {
        return placeService.getPlaceById(venueId, id);
    }


}
