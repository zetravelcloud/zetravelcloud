package com.zetravelcloud.webapp.repository;

import com.zetravelcloud.webapp.domain.Hotel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Hotel entity.
 */
public interface HotelRepository extends JpaRepository<Hotel,Long> {

}
