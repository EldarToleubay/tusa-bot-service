package com.tusa.venuebotservice.service.impl;

import com.tusa.venuebotservice.dto.PlaceDto;
import com.tusa.venuebotservice.dto.PlaceStatusDto;
import com.tusa.venuebotservice.entity.PlaceEnum;
import com.tusa.venuebotservice.entity.PlaceStatus;
import com.tusa.venuebotservice.entity.Venue;
import com.tusa.venuebotservice.mapper.PlaceDtoMapper;
import com.tusa.venuebotservice.mapper.PlaceStatusDtoMapper;
import com.tusa.venuebotservice.repository.PlaceStatusRepository;
import com.tusa.venuebotservice.service.PlaceService;
import com.tusa.venuebotservice.service.PlaceStatusService;
import com.tusa.venuebotservice.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceStatusServiceImpl implements PlaceStatusService {
    private final PlaceStatusRepository placeStatusRepository;
    private final PlaceStatusDtoMapper placeStatusDtoMapper;
    private final VenueService venueService;
    private final PlaceService placeService;
    private final PlaceDtoMapper placeDtoMapper;


    @Override
    public List<PlaceStatusDto> getPlaceAvailability(Long venueId, LocalDate date) {

        List<PlaceStatus> allStatuses = placeStatusRepository.findByDate(date);
        return allStatuses.stream()
                .filter(status -> status.getPlace().getVenue().getId().equals(venueId))
                .map(placeStatusDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlaceStatusDto> setPlaceStatus(Long venueId, Long placeId,String timSlot) {
        Venue venue = venueService.findVenueById(venueId);
        PlaceDto placeDto = placeService.getPlaceById(venueId,placeId);

        Optional<PlaceStatus> placeStatus = placeStatusRepository.findByVenueAndPlace(venue, placeDtoMapper.toEntity(placeDto));
        PlaceStatus status;
        if (placeStatus.isPresent()) {
            status = placeStatus.get();
            status.setPlace(placeDtoMapper.toEntity(placeDto));
            status.setTimeSlot(timSlot);
            status.setStatus(PlaceEnum.BOOKED);
        } else {
            status = new PlaceStatus();
            status.setPlace(placeDtoMapper.toEntity(placeDto));
            status.setVenue(venue);
            status.setDate(LocalDate.now());
            status.setTimeSlot(timSlot);
            status.setStatus(PlaceEnum.BOOKED);

        }
        placeStatusRepository.save(status);
        return List.of(placeStatusDtoMapper.toDto(status));

    }
}
