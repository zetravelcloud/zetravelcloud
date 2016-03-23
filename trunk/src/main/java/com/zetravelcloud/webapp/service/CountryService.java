package com.zetravelcloud.webapp.service;

import com.zetravelcloud.webapp.domain.Country;
import com.zetravelcloud.webapp.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Country.
 */
@Service
@Transactional
public class CountryService {

    private final Logger log = LoggerFactory.getLogger(CountryService.class);
    
    @Inject
    private CountryRepository countryRepository;
    
    /**
     * Save a country.
     * @return the persisted entity
     */
    public Country save(Country country) {
        log.debug("Request to save Country : {}", country);
        Country result = countryRepository.save(country);
        return result;
    }

    /**
     *  get all the countrys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Country> findAll() {
        log.debug("Request to get all Countrys");
        List<Country> result = countryRepository.findAll();
        return result;
    }

    /**
     *  get one country by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Country findOne(Long id) {
        log.debug("Request to get Country : {}", id);
        Country country = countryRepository.findOne(id);
        return country;
    }

    /**
     *  delete the  country by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Country : {}", id);
        countryRepository.delete(id);
    }
}
