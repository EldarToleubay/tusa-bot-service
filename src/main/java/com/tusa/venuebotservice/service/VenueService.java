package com.tusa.venuebotservice.service;

import com.tusa.venuebotservice.entity.Venue;
import io.minio.errors.*;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface VenueService {
    Venue createVenue(Venue venue);

    List<Venue> findAllVenues();

    void deleteVenue(Long id);

    Venue findVenueById(Long id);

    Venue updateVenue(Long id, Venue venue);

    ResponseEntity<Resource> getPng(Long id);

    InputStream download(String layoutUrl) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, io.minio.errors.InternalException;

}
