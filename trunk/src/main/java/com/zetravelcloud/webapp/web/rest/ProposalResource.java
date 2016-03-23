package com.zetravelcloud.webapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zetravelcloud.webapp.domain.Proposal;
import com.zetravelcloud.webapp.service.ProposalService;
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
 * REST controller for managing Proposal.
 */
@RestController
@RequestMapping("/api")
public class ProposalResource {

    private final Logger log = LoggerFactory.getLogger(ProposalResource.class);
        
    @Inject
    private ProposalService proposalService;
    
    /**
     * POST  /proposals -> Create a new proposal.
     */
    @RequestMapping(value = "/proposals",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Proposal> createProposal(@RequestBody Proposal proposal) throws URISyntaxException {
        log.debug("REST request to save Proposal : {}", proposal);
        if (proposal.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("proposal", "idexists", "A new proposal cannot already have an ID")).body(null);
        }
        Proposal result = proposalService.save(proposal);
        return ResponseEntity.created(new URI("/api/proposals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("proposal", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proposals -> Updates an existing proposal.
     */
    @RequestMapping(value = "/proposals",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Proposal> updateProposal(@RequestBody Proposal proposal) throws URISyntaxException {
        log.debug("REST request to update Proposal : {}", proposal);
        if (proposal.getId() == null) {
            return createProposal(proposal);
        }
        Proposal result = proposalService.save(proposal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("proposal", proposal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proposals -> get all the proposals.
     */
    @RequestMapping(value = "/proposals",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Proposal>> getAllProposals(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Proposals");
        Page<Proposal> page = proposalService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proposals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /proposals/:id -> get the "id" proposal.
     */
    @RequestMapping(value = "/proposals/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Proposal> getProposal(@PathVariable Long id) {
        log.debug("REST request to get Proposal : {}", id);
        Proposal proposal = proposalService.findOne(id);
        return Optional.ofNullable(proposal)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /proposals/:id -> delete the "id" proposal.
     */
    @RequestMapping(value = "/proposals/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProposal(@PathVariable Long id) {
        log.debug("REST request to delete Proposal : {}", id);
        proposalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("proposal", id.toString())).build();
    }
}
