package com.zetravelcloud.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zetravelcloud.webapp.domain.TravelerFile;
import com.zetravelcloud.webapp.service.TravelerFileService;
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
 * REST controller for managing TravelerFile.
 */
@RestController
@RequestMapping("/api")
public class TravelerFileResource {

    private final Logger log = LoggerFactory.getLogger(TravelerFileResource.class);
        
    @Inject
    private TravelerFileService travelerFileService;
    
    /**
     * POST  /travelerFiles -> Create a new travelerFile.
     */
    @RequestMapping(value = "/travelerFiles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelerFile> createTravelerFile(@RequestBody TravelerFile travelerFile) throws URISyntaxException {
        log.debug("REST request to save TravelerFile : {}", travelerFile);
        if (travelerFile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("travelerFile", "idexists", "A new travelerFile cannot already have an ID")).body(null);
        }
        TravelerFile result = travelerFileService.save(travelerFile);
        return ResponseEntity.created(new URI("/api/travelerFiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("travelerFile", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /travelerFiles -> Updates an existing travelerFile.
     */
    @RequestMapping(value = "/travelerFiles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelerFile> updateTravelerFile(@RequestBody TravelerFile travelerFile) throws URISyntaxException {
        log.debug("REST request to update TravelerFile : {}", travelerFile);
        if (travelerFile.getId() == null) {
            return createTravelerFile(travelerFile);
        }
        TravelerFile result = travelerFileService.save(travelerFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("travelerFile", travelerFile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /travelerFiles -> get all the travelerFiles.
     */
    @RequestMapping(value = "/travelerFiles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TravelerFile> getAllTravelerFiles() {
        log.debug("REST request to get all TravelerFiles");
        return travelerFileService.findAll();
            }

    /**
     * GET  /travelerFiles/:id -> get the "id" travelerFile.
     */
    @RequestMapping(value = "/travelerFiles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelerFile> getTravelerFile(@PathVariable Long id) {
        log.debug("REST request to get TravelerFile : {}", id);
        TravelerFile travelerFile = travelerFileService.findOne(id);
        return Optional.ofNullable(travelerFile)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /travelerFiles/:id -> delete the "id" travelerFile.
     */
    @RequestMapping(value = "/travelerFiles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTravelerFile(@PathVariable Long id) {
        log.debug("REST request to delete TravelerFile : {}", id);
        travelerFileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("travelerFile", id.toString())).build();
    }
}
