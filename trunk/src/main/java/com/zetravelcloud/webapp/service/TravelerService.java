package com.zetravelcloud.webapp.service;

import com.zetravelcloud.webapp.domain.Traveler;
import com.zetravelcloud.webapp.repository.TravelerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Traveler.
 */
@Service
@Transactional
public class TravelerService {

    private final Logger log = LoggerFactory.getLogger(TravelerService.class);
    
    @Inject
    private TravelerRepository travelerRepository;
    
    /**
     * Save a traveler.
     * @return the persisted entity
     */
    public Traveler save(Traveler traveler) {
        log.debug("Request to save Traveler : {}", traveler);
        Traveler result = travelerRepository.save(traveler);
        return result;
    }

    /**
     *  get all the travelers.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Traveler> findAll() {
        log.debug("Request to get all Travelers");
        List<Traveler> result = travelerRepository.findAll();
        return result;
    }

    /**
     *  get one traveler by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Traveler findOne(Long id) {
        log.debug("Request to get Traveler : {}", id);
        Traveler traveler = travelerRepository.findOne(id);
        return traveler;
    }

    /**
     *  delete the  traveler by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Traveler : {}", id);
        travelerRepository.delete(id);
    }
}
