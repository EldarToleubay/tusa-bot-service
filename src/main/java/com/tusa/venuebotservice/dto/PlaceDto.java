package com.tusa.venuebotservice.dto;

import lombok.Data;

@Data
public class PlaceDto {
    private Long id;

    private String label;
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
    private String zone;
    private Long venueId;
    private Boolean active;
}
