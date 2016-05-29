package com.zetravelcloud.webapp.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zetravelcloud.webapp.domain.TravelRequest;
import com.zetravelcloud.webapp.repository.TravelRequestRepository;
import com.zetravelcloud.webapp.repository.TravelerRepository;

/**
 * Service Implementation for managing TravelRequest.
 */
@Service
@Transactional
public class TravelRequestService {

    private final Logger log = LoggerFactory.getLogger(TravelRequestService.class);
    
    @Inject
    private TravelRequestRepository travelRequestRepository;
    
    @Inject
    private TravelerRepository travelerRepository;
    /**
     * Save a travelRequest.
     * @return the persisted entity
     */
    public TravelRequest save(TravelRequest travelRequest) {
        log.debug("Request to save TravelRequest : {}", travelRequest);
        TravelRequest result = travelRequestRepository.save(travelRequest);
        return result;
    }

    /**
     *  get all the travelRequests.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TravelRequest> findAll() {
        log.debug("Request to get all TravelRequests");
        List<TravelRequest> result = travelRequestRepository.findAll();
        return result;
    }

    /**
     *  get one travelRequest by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TravelRequest findOne(Long id) {
        log.debug("Request to get TravelRequest : {}", id);
        TravelRequest travelRequest = travelRequestRepository.findOne(id);
        return travelRequest;
    }

    @Transactional(readOnly = true) 
    public TravelRequest findOneWithDetails(Long id) {
        log.debug("Request to get TravelRequest With travelers: {}", id);
        TravelRequest travelRequest = travelRequestRepository.findOneWithDetails(id);
        return travelRequest;
    }
    
    /**
     *  delete the  travelRequest by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete TravelRequest : {}", id);
        travelRequestRepository.delete(id);
    }
}
