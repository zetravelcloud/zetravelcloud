package com.zetravelcloud.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zetravelcloud.webapp.domain.ProposedRoom;
import com.zetravelcloud.webapp.service.ProposedRoomService;
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
 * REST controller for managing ProposedRoom.
 */
@RestController
@RequestMapping("/api")
public class ProposedRoomResource {

    private final Logger log = LoggerFactory.getLogger(ProposedRoomResource.class);
        
    @Inject
    private ProposedRoomService proposedRoomService;
    
    /**
     * POST  /proposedRooms -> Create a new proposedRoom.
     */
    @RequestMapping(value = "/proposedRooms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProposedRoom> createProposedRoom(@RequestBody ProposedRoom proposedRoom) throws URISyntaxException {
        log.debug("REST request to save ProposedRoom : {}", proposedRoom);
        if (proposedRoom.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("proposedRoom", "idexists", "A new proposedRoom cannot already have an ID")).body(null);
        }
        ProposedRoom result = proposedRoomService.save(proposedRoom);
        return ResponseEntity.created(new URI("/api/proposedRooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("proposedRoom", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proposedRooms -> Updates an existing proposedRoom.
     */
    @RequestMapping(value = "/proposedRooms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProposedRoom> updateProposedRoom(@RequestBody ProposedRoom proposedRoom) throws URISyntaxException {
        log.debug("REST request to update ProposedRoom : {}", proposedRoom);
        if (proposedRoom.getId() == null) {
            return createProposedRoom(proposedRoom);
        }
        ProposedRoom result = proposedRoomService.save(proposedRoom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("proposedRoom", proposedRoom.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proposedRooms -> get all the proposedRooms.
     */
    @RequestMapping(value = "/proposedRooms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProposedRoom> getAllProposedRooms() {
        log.debug("REST request to get all ProposedRooms");
        return proposedRoomService.findAll();
            }

    /**
     * GET  /proposedRooms/:id -> get the "id" proposedRoom.
     */
    @RequestMapping(value = "/proposedRooms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProposedRoom> getProposedRoom(@PathVariable Long id) {
        log.debug("REST request to get ProposedRoom : {}", id);
        ProposedRoom proposedRoom = proposedRoomService.findOne(id);
        return Optional.ofNullable(proposedRoom)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /proposedRooms/:id -> delete the "id" proposedRoom.
     */
    @RequestMapping(value = "/proposedRooms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProposedRoom(@PathVariable Long id) {
        log.debug("REST request to delete ProposedRoom : {}", id);
        proposedRoomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("proposedRoom", id.toString())).build();
    }
}
