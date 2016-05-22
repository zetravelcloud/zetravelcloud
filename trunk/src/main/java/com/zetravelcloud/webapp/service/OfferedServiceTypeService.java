package com.zetravelcloud.webapp.service;

import com.zetravelcloud.webapp.domain.OfferedServiceType;
import com.zetravelcloud.webapp.repository.OfferedServiceTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing OfferedServiceType.
 */
@Service
@Transactional
public class OfferedServiceTypeService {

    private final Logger log = LoggerFactory.getLogger(OfferedServiceTypeService.class);
    
    @Inject
    private OfferedServiceTypeRepository offeredServiceTypeRepository;
    
    /**
     * Save a offeredServiceType.
     * @return the persisted entity
     */
    public OfferedServiceType save(OfferedServiceType offeredServiceType) {
        log.debug("Request to save OfferedServiceType : {}", offeredServiceType);
        OfferedServiceType result = offeredServiceTypeRepository.save(offeredServiceType);
        return result;
    }

    /**
     *  get all the offeredServiceTypes.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<OfferedServiceType> findAll() {
        log.debug("Request to get all OfferedServiceTypes");
        List<OfferedServiceType> result = offeredServiceTypeRepository.findAll();
        return result;
    }

    /**
     *  get one offeredServiceType by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OfferedServiceType findOne(Long id) {
        log.debug("Request to get OfferedServiceType : {}", id);
        OfferedServiceType offeredServiceType = offeredServiceTypeRepository.findOne(id);
        return offeredServiceType;
    }

    /**
     *  delete the  offeredServiceType by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete OfferedServiceType : {}", id);
        offeredServiceTypeRepository.delete(id);
    }
}
