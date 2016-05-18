package com.zetravelcloud.webapp.repository;

import com.zetravelcloud.webapp.domain.Traveler;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Traveler entity.
 */
public interface TravelerRepository extends JpaRepository<Traveler,Long> {

}
