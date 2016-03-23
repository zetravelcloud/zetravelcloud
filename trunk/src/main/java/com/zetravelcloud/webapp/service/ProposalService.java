package com.zetravelcloud.webapp.service;

import com.zetravelcloud.webapp.domain.Proposal;
import com.zetravelcloud.webapp.repository.ProposalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Proposal.
 */
@Service
@Transactional
public class ProposalService {

    private final Logger log = LoggerFactory.getLogger(ProposalService.class);
    
    @Inject
    private ProposalRepository proposalRepository;
    
    /**
     * Save a proposal.
     * @return the persisted entity
     */
    public Proposal save(Proposal proposal) {
        log.debug("Request to save Proposal : {}", proposal);
        Proposal result = proposalRepository.save(proposal);
        return result;
    }

    /**
     *  get all the proposals.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Proposal> findAll(Pageable pageable) {
        log.debug("Request to get all Proposals");
        Page<Proposal> result = proposalRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one proposal by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Proposal findOne(Long id) {
        log.debug("Request to get Proposal : {}", id);
        Proposal proposal = proposalRepository.findOne(id);
        return proposal;
    }

    /**
     *  delete the  proposal by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Proposal : {}", id);
        proposalRepository.delete(id);
    }
}
