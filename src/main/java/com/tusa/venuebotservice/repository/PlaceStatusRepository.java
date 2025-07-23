package com.tusa.venuebotservice.repository;

import com.tusa.venuebotservice.entity.Place;
import com.tusa.venuebotservice.entity.PlaceStatus;
import com.tusa.venuebotservice.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PlaceStatusRepository extends JpaRepository<PlaceStatus, Long> {
    List<PlaceStatus> findByDate(LocalDate date);

    Optional<PlaceStatus> findByVenueAndPlace(Venue venue, Place place);
}
