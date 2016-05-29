package com.zetravelcloud.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zetravelcloud.webapp.domain.TravelRequest;
import com.zetravelcloud.webapp.security.SecurityUtils;
import com.zetravelcloud.webapp.service.TravelRequestService;
import com.zetravelcloud.webapp.service.UserService;
import com.zetravelcloud.webapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TravelRequest.
 */
@RestController
@RequestMapping("/api")
public class TravelRequestResource {

    private final Logger log = LoggerFactory.getLogger(TravelRequestResource.class);
        
    @Inject
    private TravelRequestService travelRequestService;
    
    @Inject
    private UserService userService;
    
    /**
     * POST  /travelRequests -> Create a new travelRequest.
     */
    @RequestMapping(value = "/travelRequests",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelRequest> createTravelRequest(@Valid @RequestBody TravelRequest travelRequest) throws URISyntaxException {
        log.debug("REST request to save TravelRequest : {}", travelRequest);
        if (travelRequest.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("travelRequest", "idexists", "A new travelRequest cannot already have an ID")).body(null);
        }
        travelRequest.setDate(ZonedDateTime.now());
        travelRequest.setCreatedBy(userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).get());
        TravelRequest result = travelRequestService.save(travelRequest);
        return ResponseEntity.created(new URI("/api/travelRequests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("travelRequest", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /travelRequests -> Updates an existing travelRequest.
     */
    @RequestMapping(value = "/travelRequests",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelRequest> updateTravelRequest(@Valid @RequestBody TravelRequest travelRequest) throws URISyntaxException {
        log.debug("REST request to update TravelRequest : {}", travelRequest);
        if (travelRequest.getId() == null) {
            return createTravelRequest(travelRequest);
        }
        TravelRequest result = travelRequestService.save(travelRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("travelRequest", travelRequest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /travelRequests -> get all the travelRequests.
     */
    @RequestMapping(value = "/travelRequests",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TravelRequest> getAllTravelRequests() {
        log.debug("REST request to get all TravelRequests");
        return travelRequestService.findAll();
            }

    /**
     * GET  /travelRequests/:id -> get the "id" travelRequest.
     */
    @RequestMapping(value = "/travelRequests/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TravelRequest> getTravelRequest(@PathVariable Long id) {
        log.debug("REST request to get TravelRequest : {}", id);
        // TODO use query parameter to filter results
//        TravelRequest travelRequest = travelRequestService.findOne(id);
        TravelRequest travelRequest = travelRequestService.findOneWithDetails(id);
        return Optional.ofNullable(travelRequest)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /travelRequests/:id -> delete the "id" travelRequest.
     */
    @RequestMapping(value = "/travelRequests/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTravelRequest(@PathVariable Long id) {
        log.debug("REST request to delete TravelRequest : {}", id);
        travelRequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("travelRequest", id.toString())).build();
    }
}
