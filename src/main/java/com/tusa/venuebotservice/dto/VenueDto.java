package com.tusa.venuebotservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VenueDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String layoutUrl;
    private String createdBy;
    private LocalDate createdAt;
}
