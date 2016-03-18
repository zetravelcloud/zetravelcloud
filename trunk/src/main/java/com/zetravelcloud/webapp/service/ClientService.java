package com.zetravelcloud.webapp.service;

import com.zetravelcloud.webapp.domain.Client;
import com.zetravelcloud.webapp.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Client.
 */
@Service
@Transactional
public class ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientService.class);
    
    @Inject
    private ClientRepository clientRepository;
    
    /**
     * Save a client.
     * @return the persisted entity
     */
    public Client save(Client client) {
        log.debug("Request to save Client : {}", client);
        Client result = clientRepository.save(client);
        return result;
    }

    /**
     *  get all the clients.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Client> findAll() {
        log.debug("Request to get all Clients");
        List<Client> result = clientRepository.findAll();
        return result;
    }

    /**
     *  get one client by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Client findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        Client client = clientRepository.findOne(id);
        return client;
    }

    /**
     *  delete the  client by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.delete(id);
    }
}
