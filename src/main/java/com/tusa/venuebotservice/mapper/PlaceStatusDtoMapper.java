package com.tusa.venuebotservice.mapper;

import com.tusa.venuebotservice.dto.PlaceStatusDto;
import com.tusa.venuebotservice.entity.Place;
import com.tusa.venuebotservice.entity.PlaceStatus;
import com.tusa.venuebotservice.entity.Venue;
import com.tusa.venuebotservice.service.PlaceService;
import com.tusa.venuebotservice.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceStatusDtoMapper {
    private final PlaceService placeService;
    private final VenueService venueService;
    private final PlaceDtoMapper placeDtoMapper;


    public PlaceStatusDto toDto(PlaceStatus placeStatus) {
        PlaceStatusDto placeStatusDto = new PlaceStatusDto();
        placeStatusDto.setId(placeStatus.getId());
        placeStatusDto.setStatus(placeStatus.getStatus());
        placeStatusDto.setPlaceId(placeStatus.getPlace().getId());
        placeStatusDto.setVenueId(placeStatus.getVenue().getId());
        placeStatusDto.setTimeSlot(placeStatus.getTimeSlot());
        placeStatusDto.setDate(placeStatus.getDate());
        return placeStatusDto;
    }

    public PlaceStatus toEntity(PlaceStatusDto placeStatusDto) {
        PlaceStatus placeStatus = new PlaceStatus();
        placeStatus.setId(placeStatusDto.getId());
        placeStatus.setStatus(placeStatusDto.getStatus());
        placeStatus.setStatus(placeStatusDto.getStatus());
        Place place = placeDtoMapper.toEntity(
                placeService.getPlaceById(placeStatusDto.getVenueId(), placeStatusDto.getPlaceId())
        );
        placeStatus.setPlace(place);
        Venue venue = venueService.findVenueById(placeStatusDto.getVenueId());
        placeStatus.setVenue(venue);
        placeStatus.setTimeSlot(placeStatusDto.getTimeSlot());
        placeStatus.setDate(placeStatusDto.getDate());
        return placeStatus;
    }
}
