package com.tusa.venuebotservice.repository;

import com.tusa.venuebotservice.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {

}
