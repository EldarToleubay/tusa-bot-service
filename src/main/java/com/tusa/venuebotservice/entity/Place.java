package com.tusa.venuebotservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tables")
@Data
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
    private String zone;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    private Boolean active;


}
