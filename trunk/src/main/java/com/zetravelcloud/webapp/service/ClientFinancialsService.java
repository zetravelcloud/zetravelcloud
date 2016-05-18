package com.zetravelcloud.webapp.service;

import com.zetravelcloud.webapp.domain.ClientFinancials;
import com.zetravelcloud.webapp.repository.ClientFinancialsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ClientFinancials.
 */
@Service
@Transactional
public class ClientFinancialsService {

    private final Logger log = LoggerFactory.getLogger(ClientFinancialsService.class);
    
    @Inject
    private ClientFinancialsRepository clientFinancialsRepository;
    
    /**
     * Save a clientFinancials.
     * @return the persisted entity
     */
    public ClientFinancials save(ClientFinancials clientFinancials) {
        log.debug("Request to save ClientFinancials : {}", clientFinancials);
        ClientFinancials result = clientFinancialsRepository.save(clientFinancials);
        return result;
    }

    /**
     *  get all the clientFinancialss.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ClientFinancials> findAll() {
        log.debug("Request to get all ClientFinancialss");
        List<ClientFinancials> result = clientFinancialsRepository.findAll();
        return result;
    }

    /**
     *  get one clientFinancials by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ClientFinancials findOne(Long id) {
        log.debug("Request to get ClientFinancials : {}", id);
        ClientFinancials clientFinancials = clientFinancialsRepository.findOne(id);
        return clientFinancials;
    }

    /**
     *  delete the  clientFinancials by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientFinancials : {}", id);
        clientFinancialsRepository.delete(id);
    }
}
