package com.tusa.venuebotservice.service.impl;

import com.tusa.venuebotservice.dto.PlaceDto;
import com.tusa.venuebotservice.entity.Place;
import com.tusa.venuebotservice.entity.Venue;
import com.tusa.venuebotservice.mapper.PlaceDtoMapper;
import com.tusa.venuebotservice.repository.PlaceRepository;
import com.tusa.venuebotservice.service.PlaceService;
import com.tusa.venuebotservice.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final VenueService venueService;
    private final PlaceDtoMapper placeDtoMapper;

    @Override
    public PlaceDto addPlace(Long venueId, PlaceDto placeDto) {
        Venue venue = venueService.findVenueById(venueId);
        Place place = new Place();
        place.setLabel(placeDto.getLabel());
        place.setX(placeDto.getX());
        place.setY(placeDto.getY());
        place.setWidth(placeDto.getWidth());
        place.setHeight(placeDto.getHeight());
        place.setZone(placeDto.getZone());
        place.setVenue(venue);
        place.setActive(placeDto.getActive());
        Place placeSave = placeRepository.save(place);
        return placeDtoMapper.toDto(placeSave);
    }

    @Override
    public List<PlaceDto> getAllPlace(Long venueId) {
        return placeRepository.findByVenueId(venueId)
                .stream()
                .map(placeDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlaceDto getPlaceById(Long venueId, Long id) {
        Place place = placeRepository.findById(id)
                .filter(e -> e.getVenue().getId().equals(venueId))
                .orElseThrow(() -> new RuntimeException("Place not found"));

        return placeDtoMapper.toDto(place);
    }


}
