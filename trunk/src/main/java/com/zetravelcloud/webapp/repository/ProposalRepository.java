package com.zetravelcloud.webapp.repository;

import com.zetravelcloud.webapp.domain.Proposal;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Proposal entity.
 */
public interface ProposalRepository extends JpaRepository<Proposal,Long> {

}
