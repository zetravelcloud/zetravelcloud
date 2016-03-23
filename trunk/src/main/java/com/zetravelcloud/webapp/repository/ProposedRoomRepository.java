package com.zetravelcloud.webapp.repository;

import com.zetravelcloud.webapp.domain.ProposedRoom;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProposedRoom entity.
 */
public interface ProposedRoomRepository extends JpaRepository<ProposedRoom,Long> {

}
