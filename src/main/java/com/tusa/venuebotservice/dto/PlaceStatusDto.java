package com.tusa.venuebotservice.dto;

import com.tusa.venuebotservice.entity.PlaceEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlaceStatusDto {

    private Long id;
    private Long placeId;
    private Long venueId;

    private LocalDate date;
    private String timeSlot;

    private PlaceEnum status;
}
