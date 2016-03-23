package com.zetravelcloud.webapp.service;

import com.zetravelcloud.webapp.domain.City;
import com.zetravelcloud.webapp.repository.CityRepository;
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
 * Service Implementation for managing City.
 */
@Service
@Transactional
public class CityService {

    private final Logger log = LoggerFactory.getLogger(CityService.class);
    
    @Inject
    private CityRepository cityRepository;
    
    /**
     * Save a city.
     * @return the persisted entity
     */
    public City save(City city) {
        log.debug("Request to save City : {}", city);
        City result = cityRepository.save(city);
        return result;
    }

    /**
     *  get all the citys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<City> findAll(Pageable pageable) {
        log.debug("Request to get all Citys");
        Page<City> result = cityRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one city by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public City findOne(Long id) {
        log.debug("Request to get City : {}", id);
        City city = cityRepository.findOne(id);
        return city;
    }

    /**
     *  delete the  city by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete City : {}", id);
        cityRepository.delete(id);
    }
}
