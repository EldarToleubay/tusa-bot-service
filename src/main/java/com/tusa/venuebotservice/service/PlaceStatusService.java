package com.tusa.venuebotservice.service;


import com.tusa.venuebotservice.dto.PlaceStatusDto;

import java.time.LocalDate;
import java.util.List;

public interface PlaceStatusService {
    List<PlaceStatusDto> getPlaceAvailability(Long venueId, LocalDate date);

    List<PlaceStatusDto> setPlaceStatus(Long venueId, Long placeId,String timeSlot);
}
