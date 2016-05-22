package com.zetravelcloud.webapp.service;

import com.zetravelcloud.webapp.domain.ServiceProvider;
import com.zetravelcloud.webapp.repository.ServiceProviderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ServiceProvider.
 */
@Service
@Transactional
public class ServiceProviderService {

    private final Logger log = LoggerFactory.getLogger(ServiceProviderService.class);
    
    @Inject
    private ServiceProviderRepository serviceProviderRepository;
    
    /**
     * Save a serviceProvider.
     * @return the persisted entity
     */
    public ServiceProvider save(ServiceProvider serviceProvider) {
        log.debug("Request to save ServiceProvider : {}", serviceProvider);
        ServiceProvider result = serviceProviderRepository.save(serviceProvider);
        return result;
    }

    /**
     *  get all the serviceProviders.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ServiceProvider> findAll() {
        log.debug("Request to get all ServiceProviders");
        List<ServiceProvider> result = serviceProviderRepository.findAll();
        return result;
    }

    /**
     *  get one serviceProvider by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ServiceProvider findOne(Long id) {
        log.debug("Request to get ServiceProvider : {}", id);
        ServiceProvider serviceProvider = serviceProviderRepository.findOne(id);
        return serviceProvider;
    }

    /**
     *  delete the  serviceProvider by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServiceProvider : {}", id);
        serviceProviderRepository.delete(id);
    }
}
