package com.tusa.venuebotservice.repository;

import com.tusa.venuebotservice.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place,Long> {
    List<Place> findByVenueId(Long venueId);
}
