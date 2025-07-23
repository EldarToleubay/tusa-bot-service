package com.tusa.venuebotservice.service;


import com.tusa.venuebotservice.dto.PlaceDto;

import java.util.List;

public interface PlaceService {
    PlaceDto addPlace(Long venueId, PlaceDto placeDto);

    List<PlaceDto> getAllPlace(Long venueId);


    PlaceDto getPlaceById(Long venueId, Long id);
}
