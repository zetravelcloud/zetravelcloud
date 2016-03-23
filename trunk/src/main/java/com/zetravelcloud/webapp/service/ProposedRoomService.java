package com.zetravelcloud.webapp.service;

import com.zetravelcloud.webapp.domain.ProposedRoom;
import com.zetravelcloud.webapp.repository.ProposedRoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ProposedRoom.
 */
@Service
@Transactional
public class ProposedRoomService {

    private final Logger log = LoggerFactory.getLogger(ProposedRoomService.class);
    
    @Inject
    private ProposedRoomRepository proposedRoomRepository;
    
    /**
     * Save a proposedRoom.
     * @return the persisted entity
     */
    public ProposedRoom save(ProposedRoom proposedRoom) {
        log.debug("Request to save ProposedRoom : {}", proposedRoom);
        ProposedRoom result = proposedRoomRepository.save(proposedRoom);
        return result;
    }

    /**
     *  get all the proposedRooms.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ProposedRoom> findAll() {
        log.debug("Request to get all ProposedRooms");
        List<ProposedRoom> result = proposedRoomRepository.findAll();
        return result;
    }

    /**
     *  get one proposedRoom by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProposedRoom findOne(Long id) {
        log.debug("Request to get ProposedRoom : {}", id);
        ProposedRoom proposedRoom = proposedRoomRepository.findOne(id);
        return proposedRoom;
    }

    /**
     *  delete the  proposedRoom by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProposedRoom : {}", id);
        proposedRoomRepository.delete(id);
    }
}
