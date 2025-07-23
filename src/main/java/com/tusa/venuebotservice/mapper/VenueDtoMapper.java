package com.tusa.venuebotservice.mapper;

import com.tusa.venuebotservice.dto.VenueDto;
import com.tusa.venuebotservice.entity.Venue;
import org.springframework.stereotype.Component;

@Component
public class VenueDtoMapper {

    public Venue toEntity(VenueDto venueDto) {
        Venue venue = new Venue();
        venue.setId(venueDto.getId());
        venue.setName(venueDto.getName());
        venue.setDescription(venueDto.getDescription());
        venue.setAddress(venueDto.getAddress());
        venue.setCreatedBy(venueDto.getCreatedBy());
        venue.setCreatedAt(venueDto.getCreatedAt());
        venue.setLayoutUrl(venueDto.getLayoutUrl());
        return venue;
    }

    public VenueDto toDto(Venue venue) {
        VenueDto venueDto = new VenueDto();
        venueDto.setId(venue.getId());
        venueDto.setName(venue.getName());
        venueDto.setDescription(venue.getDescription());
        venueDto.setAddress(venue.getAddress());
        venueDto.setCreatedBy(venue.getCreatedBy());
        venueDto.setCreatedAt(venue.getCreatedAt());
        venueDto.setLayoutUrl(venue.getLayoutUrl());
        return venueDto;
    }

}
