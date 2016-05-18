package com.zetravelcloud.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zetravelcloud.webapp.domain.ClientFinancials;
import com.zetravelcloud.webapp.service.ClientFinancialsService;
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
 * REST controller for managing ClientFinancials.
 */
@RestController
@RequestMapping("/api")
public class ClientFinancialsResource {

    private final Logger log = LoggerFactory.getLogger(ClientFinancialsResource.class);
        
    @Inject
    private ClientFinancialsService clientFinancialsService;
    
    /**
     * POST  /clientFinancialss -> Create a new clientFinancials.
     */
    @RequestMapping(value = "/clientFinancialss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClientFinancials> createClientFinancials(@RequestBody ClientFinancials clientFinancials) throws URISyntaxException {
        log.debug("REST request to save ClientFinancials : {}", clientFinancials);
        if (clientFinancials.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("clientFinancials", "idexists", "A new clientFinancials cannot already have an ID")).body(null);
        }
        ClientFinancials result = clientFinancialsService.save(clientFinancials);
        return ResponseEntity.created(new URI("/api/clientFinancialss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("clientFinancials", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clientFinancialss -> Updates an existing clientFinancials.
     */
    @RequestMapping(value = "/clientFinancialss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClientFinancials> updateClientFinancials(@RequestBody ClientFinancials clientFinancials) throws URISyntaxException {
        log.debug("REST request to update ClientFinancials : {}", clientFinancials);
        if (clientFinancials.getId() == null) {
            return createClientFinancials(clientFinancials);
        }
        ClientFinancials result = clientFinancialsService.save(clientFinancials);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("clientFinancials", clientFinancials.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clientFinancialss -> get all the clientFinancialss.
     */
    @RequestMapping(value = "/clientFinancialss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ClientFinancials> getAllClientFinancialss() {
        log.debug("REST request to get all ClientFinancialss");
        return clientFinancialsService.findAll();
            }

    /**
     * GET  /clientFinancialss/:id -> get the "id" clientFinancials.
     */
    @RequestMapping(value = "/clientFinancialss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClientFinancials> getClientFinancials(@PathVariable Long id) {
        log.debug("REST request to get ClientFinancials : {}", id);
        ClientFinancials clientFinancials = clientFinancialsService.findOne(id);
        return Optional.ofNullable(clientFinancials)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /clientFinancialss/:id -> delete the "id" clientFinancials.
     */
    @RequestMapping(value = "/clientFinancialss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteClientFinancials(@PathVariable Long id) {
        log.debug("REST request to delete ClientFinancials : {}", id);
        clientFinancialsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("clientFinancials", id.toString())).build();
    }
}
