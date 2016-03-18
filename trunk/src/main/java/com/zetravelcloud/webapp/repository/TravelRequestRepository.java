package com.zetravelcloud.webapp.repository;

import com.zetravelcloud.webapp.domain.TravelRequest;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TravelRequest entity.
 */
public interface TravelRequestRepository extends JpaRepository<TravelRequest,Long> {

    @Query("select travelRequest from TravelRequest travelRequest where travelRequest.createdBy.login = ?#{principal.username}")
    List<TravelRequest> findByCreatedByIsCurrentUser();

}
