package com.zetravelcloud.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zetravelcloud.webapp.domain.TravelRequest;

/**
 * Spring Data JPA repository for the TravelRequest entity.
 */
public interface TravelRequestRepository extends JpaRepository<TravelRequest,Long> {

    @Query("select travelRequest from TravelRequest travelRequest where travelRequest.createdBy.login = ?#{principal.username}")
    List<TravelRequest> findByCreatedByIsCurrentUser();

    @Query("select travelRequest from TravelRequest travelRequest left join fetch travelRequest.travelers left join fetch travelRequest.offeredServices where travelRequest.id =:id")
    TravelRequest findOneWithDetails(@Param("id") Long id);

}
