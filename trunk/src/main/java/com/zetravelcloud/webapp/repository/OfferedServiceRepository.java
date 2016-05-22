package com.zetravelcloud.webapp.repository;

import com.zetravelcloud.webapp.domain.OfferedService;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OfferedService entity.
 */
public interface OfferedServiceRepository extends JpaRepository<OfferedService,Long> {

}
