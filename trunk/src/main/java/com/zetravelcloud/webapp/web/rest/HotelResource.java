package com.zetravelcloud.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zetravelcloud.webapp.domain.Hotel;
import com.zetravelcloud.webapp.service.HotelService;
import com.zetravelcloud.webapp.web.rest.util.HeaderUtil;
import com.zetravelcloud.webapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Hotel.
 */
@RestController
@RequestMapping("/api")
public class HotelResource {

    private final Logger log = LoggerFactory.getLogger(HotelResource.class);
        
    @Inject
    private HotelService hotelService;
    
    /**
     * POST  /hotels -> Create a new hotel.
     */
    @RequestMapping(value = "/hotels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) throws URISyntaxException {
        log.debug("REST request to save Hotel : {}", hotel);
        if (hotel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hotel", "idexists", "A new hotel cannot already have an ID")).body(null);
        }
        Hotel result = hotelService.save(hotel);
        return ResponseEntity.created(new URI("/api/hotels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hotel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hotels -> Updates an existing hotel.
     */
    @RequestMapping(value = "/hotels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel) throws URISyntaxException {
        log.debug("REST request to update Hotel : {}", hotel);
        if (hotel.getId() == null) {
            return createHotel(hotel);
        }
        Hotel result = hotelService.save(hotel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hotel", hotel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hotels -> get all the hotels.
     */
    @RequestMapping(value = "/hotels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Hotel>> getAllHotels(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Hotels");
        Page<Hotel> page = hotelService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hotels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hotels/:id -> get the "id" hotel.
     */
    @RequestMapping(value = "/hotels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Hotel> getHotel(@PathVariable Long id) {
        log.debug("REST request to get Hotel : {}", id);
        Hotel hotel = hotelService.findOne(id);
        return Optional.ofNullable(hotel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hotels/:id -> delete the "id" hotel.
     */
    @RequestMapping(value = "/hotels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        log.debug("REST request to delete Hotel : {}", id);
        hotelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hotel", id.toString())).build();
    }
}
