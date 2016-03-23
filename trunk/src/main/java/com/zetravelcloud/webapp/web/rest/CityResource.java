package com.zetravelcloud.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zetravelcloud.webapp.domain.City;
import com.zetravelcloud.webapp.service.CityService;
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
 * REST controller for managing City.
 */
@RestController
@RequestMapping("/api")
public class CityResource {

    private final Logger log = LoggerFactory.getLogger(CityResource.class);
        
    @Inject
    private CityService cityService;
    
    /**
     * POST  /citys -> Create a new city.
     */
    @RequestMapping(value = "/citys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<City> createCity(@RequestBody City city) throws URISyntaxException {
        log.debug("REST request to save City : {}", city);
        if (city.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("city", "idexists", "A new city cannot already have an ID")).body(null);
        }
        City result = cityService.save(city);
        return ResponseEntity.created(new URI("/api/citys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("city", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /citys -> Updates an existing city.
     */
    @RequestMapping(value = "/citys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<City> updateCity(@RequestBody City city) throws URISyntaxException {
        log.debug("REST request to update City : {}", city);
        if (city.getId() == null) {
            return createCity(city);
        }
        City result = cityService.save(city);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("city", city.getId().toString()))
            .body(result);
    }

    /**
     * GET  /citys -> get all the citys.
     */
    @RequestMapping(value = "/citys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<City>> getAllCitys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Citys");
        Page<City> page = cityService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/citys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /citys/:id -> get the "id" city.
     */
    @RequestMapping(value = "/citys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<City> getCity(@PathVariable Long id) {
        log.debug("REST request to get City : {}", id);
        City city = cityService.findOne(id);
        return Optional.ofNullable(city)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /citys/:id -> delete the "id" city.
     */
    @RequestMapping(value = "/citys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        log.debug("REST request to delete City : {}", id);
        cityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("city", id.toString())).build();
    }
}
