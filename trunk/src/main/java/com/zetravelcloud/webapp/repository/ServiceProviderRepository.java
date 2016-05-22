package com.zetravelcloud.webapp.repository;

import com.zetravelcloud.webapp.domain.ServiceProvider;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ServiceProvider entity.
 */
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider,Long> {

}
