package com.zetravelcloud.webapp.repository;

import com.zetravelcloud.webapp.domain.OfferedServiceType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OfferedServiceType entity.
 */
public interface OfferedServiceTypeRepository extends JpaRepository<OfferedServiceType,Long> {

}
