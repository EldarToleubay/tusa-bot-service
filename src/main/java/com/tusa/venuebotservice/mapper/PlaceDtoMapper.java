package com.tusa.venuebotservice.mapper;

import com.tusa.venuebotservice.dto.PlaceDto;
import com.tusa.venuebotservice.entity.Place;
import com.tusa.venuebotservice.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceDtoMapper {
    private final VenueService venueService;


    public PlaceDto toDto(Place place) {
        PlaceDto dto = new PlaceDto();
        dto.setId(place.getId());
        dto.setLabel(place.getLabel());
        dto.setX(place.getX());
        dto.setY(place.getY());
        dto.setWidth(place.getWidth());
        dto.setHeight(place.getHeight());
        dto.setVenueId(place.getVenue().getId());
        dto.setActive(place.getActive());
        return dto;
    }

    public Place toEntity(PlaceDto placeDto) {
        Place place = new Place();
        place.setId(placeDto.getId());
        place.setLabel(placeDto.getLabel());
        place.setX(placeDto.getX());
        place.setY(placeDto.getY());
        place.setWidth(placeDto.getWidth());
        place.setHeight(placeDto.getHeight());
        place.setVenue(venueService.findVenueById(placeDto.getVenueId()));
        place.setActive(placeDto.getActive());
        return place;
    }
}
