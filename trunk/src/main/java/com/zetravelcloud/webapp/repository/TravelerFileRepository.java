package com.zetravelcloud.webapp.repository;

import com.zetravelcloud.webapp.domain.TravelerFile;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TravelerFile entity.
 */
public interface TravelerFileRepository extends JpaRepository<TravelerFile,Long> {

}
