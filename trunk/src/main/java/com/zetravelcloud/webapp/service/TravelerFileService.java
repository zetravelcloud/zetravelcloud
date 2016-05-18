package com.zetravelcloud.webapp.service;

import com.zetravelcloud.webapp.domain.TravelerFile;
import com.zetravelcloud.webapp.repository.TravelerFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing TravelerFile.
 */
@Service
@Transactional
public class TravelerFileService {

    private final Logger log = LoggerFactory.getLogger(TravelerFileService.class);
    
    @Inject
    private TravelerFileRepository travelerFileRepository;
    
    /**
     * Save a travelerFile.
     * @return the persisted entity
     */
    public TravelerFile save(TravelerFile travelerFile) {
        log.debug("Request to save TravelerFile : {}", travelerFile);
        TravelerFile result = travelerFileRepository.save(travelerFile);
        return result;
    }

    /**
     *  get all the travelerFiles.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TravelerFile> findAll() {
        log.debug("Request to get all TravelerFiles");
        List<TravelerFile> result = travelerFileRepository.findAll();
        return result;
    }

    /**
     *  get one travelerFile by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TravelerFile findOne(Long id) {
        log.debug("Request to get TravelerFile : {}", id);
        TravelerFile travelerFile = travelerFileRepository.findOne(id);
        return travelerFile;
    }

    /**
     *  delete the  travelerFile by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete TravelerFile : {}", id);
        travelerFileRepository.delete(id);
    }
}
