package com.zetravelcloud.webapp.service;

import com.zetravelcloud.webapp.domain.TravelRequest;
import com.zetravelcloud.webapp.repository.TravelRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing TravelRequest.
 */
@Service
@Transactional
public class TravelRequestService {

    private final Logger log = LoggerFactory.getLogger(TravelRequestService.class);
    
    @Inject
    private TravelRequestRepository travelRequestRepository;
    
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

    /**
     *  delete the  travelRequest by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete TravelRequest : {}", id);
        travelRequestRepository.delete(id);
    }
}
