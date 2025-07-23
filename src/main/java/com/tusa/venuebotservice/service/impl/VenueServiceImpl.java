package com.tusa.venuebotservice.service.impl;

import com.tusa.venuebotservice.entity.Venue;
import com.tusa.venuebotservice.repository.VenueRepository;
import com.tusa.venuebotservice.service.VenueService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@RequiredArgsConstructor

public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;
    private final MinioClient minioClient;


    @Override
    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }


    @Override
    public List<Venue> findAllVenues() {
        return venueRepository.findAll();
    }

    @Override
    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }

    @Override
    public Venue findVenueById(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public Venue updateVenue(Long id, Venue venue) {
        Venue ven = findVenueById(id);
        ven.setName(venue.getName());
        ven.setAddress(venue.getAddress());
        ven.setLayoutUrl(venue.getLayoutUrl());
        ven.setDescription(venue.getDescription());
        ven.setCreatedAt(venue.getCreatedAt());
        ven.setCreatedBy(venue.getCreatedBy());
        return venueRepository.save(ven);
    }

    @Override
    public ResponseEntity<Resource> getPng(Long id) {
        return null;
    }

    @Override

    public InputStream download(String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("venue-scheme")
                        .object(fileName)
                        .build()
        );
    }
}
