package com.zetravelcloud.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zetravelcloud.webapp.domain.OfferedServiceType;
import com.zetravelcloud.webapp.service.OfferedServiceTypeService;
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
 * REST controller for managing OfferedServiceType.
 */
@RestController
@RequestMapping("/api")
public class OfferedServiceTypeResource {

    private final Logger log = LoggerFactory.getLogger(OfferedServiceTypeResource.class);
        
    @Inject
    private OfferedServiceTypeService offeredServiceTypeService;
    
    /**
     * POST  /offeredServiceTypes -> Create a new offeredServiceType.
     */
    @RequestMapping(value = "/offeredServiceTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OfferedServiceType> createOfferedServiceType(@RequestBody OfferedServiceType offeredServiceType) throws URISyntaxException {
        log.debug("REST request to save OfferedServiceType : {}", offeredServiceType);
        if (offeredServiceType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("offeredServiceType", "idexists", "A new offeredServiceType cannot already have an ID")).body(null);
        }
        OfferedServiceType result = offeredServiceTypeService.save(offeredServiceType);
        return ResponseEntity.created(new URI("/api/offeredServiceTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("offeredServiceType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /offeredServiceTypes -> Updates an existing offeredServiceType.
     */
    @RequestMapping(value = "/offeredServiceTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OfferedServiceType> updateOfferedServiceType(@RequestBody OfferedServiceType offeredServiceType) throws URISyntaxException {
        log.debug("REST request to update OfferedServiceType : {}", offeredServiceType);
        if (offeredServiceType.getId() == null) {
            return createOfferedServiceType(offeredServiceType);
        }
        OfferedServiceType result = offeredServiceTypeService.save(offeredServiceType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("offeredServiceType", offeredServiceType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /offeredServiceTypes -> get all the offeredServiceTypes.
     */
    @RequestMapping(value = "/offeredServiceTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<OfferedServiceType> getAllOfferedServiceTypes() {
        log.debug("REST request to get all OfferedServiceTypes");
        return offeredServiceTypeService.findAll();
            }

    /**
     * GET  /offeredServiceTypes/:id -> get the "id" offeredServiceType.
     */
    @RequestMapping(value = "/offeredServiceTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OfferedServiceType> getOfferedServiceType(@PathVariable Long id) {
        log.debug("REST request to get OfferedServiceType : {}", id);
        OfferedServiceType offeredServiceType = offeredServiceTypeService.findOne(id);
        return Optional.ofNullable(offeredServiceType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /offeredServiceTypes/:id -> delete the "id" offeredServiceType.
     */
    @RequestMapping(value = "/offeredServiceTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOfferedServiceType(@PathVariable Long id) {
        log.debug("REST request to delete OfferedServiceType : {}", id);
        offeredServiceTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("offeredServiceType", id.toString())).build();
    }
}
