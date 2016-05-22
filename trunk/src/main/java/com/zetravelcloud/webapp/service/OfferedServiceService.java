package com.zetravelcloud.webapp.service;

import com.zetravelcloud.webapp.domain.OfferedService;
import com.zetravelcloud.webapp.repository.OfferedServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing OfferedService.
 */
@Service
@Transactional
public class OfferedServiceService {

    private final Logger log = LoggerFactory.getLogger(OfferedServiceService.class);
    
    @Inject
    private OfferedServiceRepository offeredServiceRepository;
    
    /**
     * Save a offeredService.
     * @return the persisted entity
     */
    public OfferedService save(OfferedService offeredService) {
        log.debug("Request to save OfferedService : {}", offeredService);
        OfferedService result = offeredServiceRepository.save(offeredService);
        return result;
    }

    /**
     *  get all the offeredServices.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<OfferedService> findAll() {
        log.debug("Request to get all OfferedServices");
        List<OfferedService> result = offeredServiceRepository.findAll();
        return result;
    }

    /**
     *  get one offeredService by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OfferedService findOne(Long id) {
        log.debug("Request to get OfferedService : {}", id);
        OfferedService offeredService = offeredServiceRepository.findOne(id);
        return offeredService;
    }

    /**
     *  delete the  offeredService by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete OfferedService : {}", id);
        offeredServiceRepository.delete(id);
    }
}
