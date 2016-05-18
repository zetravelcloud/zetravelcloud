package com.zetravelcloud.webapp.repository;

import com.zetravelcloud.webapp.domain.ClientFinancials;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClientFinancials entity.
 */
public interface ClientFinancialsRepository extends JpaRepository<ClientFinancials,Long> {

}
