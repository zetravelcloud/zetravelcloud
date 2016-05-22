package com.zetravelcloud.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zetravelcloud.webapp.domain.OfferedService;
import com.zetravelcloud.webapp.service.OfferedServiceService;
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
 * REST controller for managing OfferedService.
 */
@RestController
@RequestMapping("/api")
public class OfferedServiceResource {

    private final Logger log = LoggerFactory.getLogger(OfferedServiceResource.class);
        
    @Inject
    private OfferedServiceService offeredServiceService;
    
    /**
     * POST  /offeredServices -> Create a new offeredService.
     */
    @RequestMapping(value = "/offeredServices",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OfferedService> createOfferedService(@RequestBody OfferedService offeredService) throws URISyntaxException {
        log.debug("REST request to save OfferedService : {}", offeredService);
        if (offeredService.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("offeredService", "idexists", "A new offeredService cannot already have an ID")).body(null);
        }
        OfferedService result = offeredServiceService.save(offeredService);
        return ResponseEntity.created(new URI("/api/offeredServices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("offeredService", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /offeredServices -> Updates an existing offeredService.
     */
    @RequestMapping(value = "/offeredServices",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OfferedService> updateOfferedService(@RequestBody OfferedService offeredService) throws URISyntaxException {
        log.debug("REST request to update OfferedService : {}", offeredService);
        if (offeredService.getId() == null) {
            return createOfferedService(offeredService);
        }
        OfferedService result = offeredServiceService.save(offeredService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("offeredService", offeredService.getId().toString()))
            .body(result);
    }

    /**
     * GET  /offeredServices -> get all the offeredServices.
     */
    @RequestMapping(value = "/offeredServices",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<OfferedService> getAllOfferedServices() {
        log.debug("REST request to get all OfferedServices");
        return offeredServiceService.findAll();
            }

    /**
     * GET  /offeredServices/:id -> get the "id" offeredService.
     */
    @RequestMapping(value = "/offeredServices/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OfferedService> getOfferedService(@PathVariable Long id) {
        log.debug("REST request to get OfferedService : {}", id);
        OfferedService offeredService = offeredServiceService.findOne(id);
        return Optional.ofNullable(offeredService)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /offeredServices/:id -> delete the "id" offeredService.
     */
    @RequestMapping(value = "/offeredServices/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOfferedService(@PathVariable Long id) {
        log.debug("REST request to delete OfferedService : {}", id);
        offeredServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("offeredService", id.toString())).build();
    }
}
