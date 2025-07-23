package com.tusa.venuebotservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "table-status")
public class PlaceStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    private LocalDate date;
    private String timeSlot;

    @Enumerated(EnumType.STRING)
    private PlaceEnum status;

}
