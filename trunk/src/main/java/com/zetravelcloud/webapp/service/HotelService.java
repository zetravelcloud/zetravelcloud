package com.zetravelcloud.webapp.service;

import com.zetravelcloud.webapp.domain.Hotel;
import com.zetravelcloud.webapp.repository.HotelRepository;
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
 * Service Implementation for managing Hotel.
 */
@Service
@Transactional
public class HotelService {

    private final Logger log = LoggerFactory.getLogger(HotelService.class);
    
    @Inject
    private HotelRepository hotelRepository;
    
    /**
     * Save a hotel.
     * @return the persisted entity
     */
    public Hotel save(Hotel hotel) {
        log.debug("Request to save Hotel : {}", hotel);
        Hotel result = hotelRepository.save(hotel);
        return result;
    }

    /**
     *  get all the hotels.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Hotel> findAll(Pageable pageable) {
        log.debug("Request to get all Hotels");
        Page<Hotel> result = hotelRepository.findAll(pageable); 
        return result;
    }
    
    /**
     *  get all the hotels.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Hotel> findByExample(Hotel hotel) {
        log.debug("Request to get all Hotels");
        List<Hotel> result = hotelRepository.findByExample(hotel);
        return result;
    }


    /**
     *  get one hotel by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Hotel findOne(Long id) {
        log.debug("Request to get Hotel : {}", id);
        Hotel hotel = hotelRepository.findOne(id);
        return hotel;
    }

    /**
     *  delete the  hotel by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Hotel : {}", id);
        hotelRepository.delete(id);
    }
}
