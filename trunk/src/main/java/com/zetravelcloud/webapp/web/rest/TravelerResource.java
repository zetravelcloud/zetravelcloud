package com.zetravelcloud.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zetravelcloud.webapp.domain.Traveler;
import com.zetravelcloud.webapp.service.TravelerService;
import com.zetravelcloud.webapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Traveler.
 */
@RestController
@RequestMapping("/api")
public class TravelerResource {

    private final Logger log = LoggerFactory.getLogger(TravelerResource.class);
        
    @Inject
    private TravelerService travelerService;
    
    /**
     * POST  /travelers -> Create a new traveler.
     */
    @RequestMapping(value = "/travelers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Traveler> createTraveler(@RequestBody Traveler traveler) throws URISyntaxException {
        log.debug("REST request to save Traveler : {}", traveler);
        if (traveler.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("traveler", "idexists", "A new traveler cannot already have an ID")).body(null);
        }
        Traveler result = travelerService.save(traveler);
        return ResponseEntity.created(new URI("/api/travelers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("traveler", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /travelers -> Updates an existing traveler.
     */
    @RequestMapping(value = "/travelers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Traveler> updateTraveler(@RequestBody Traveler traveler) throws URISyntaxException {
        log.debug("REST request to update Traveler : {}", traveler);
        if (traveler.getId() == null) {
            return createTraveler(traveler);
        }
        Traveler result = travelerService.save(traveler);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("traveler", traveler.getId().toString()))
            .body(result);
    }

    /**
     * GET  /travelers -> get all the travelers.
     */
    @RequestMapping(value = "/travelers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Traveler> getAllTravelers() {
        log.debug("REST request to get all Travelers");
        return travelerService.findAll();
            }

    /**
     * GET  /travelers/:id -> get the "id" traveler.
     */
    @RequestMapping(value = "/travelers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Traveler> getTraveler(@PathVariable Long id) {
        log.debug("REST request to get Traveler : {}", id);
        Traveler traveler = travelerService.findOne(id);
        return Optional.ofNullable(traveler)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /travelers/:id -> delete the "id" traveler.
     */
    @RequestMapping(value = "/travelers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTraveler(@PathVariable Long id) {
        log.debug("REST request to delete Traveler : {}", id);
        travelerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traveler", id.toString())).build();
    }
}
