package com.zetravelcloud.webapp.repository;

import com.zetravelcloud.webapp.domain.Room;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Room entity.
 */
public interface RoomRepository extends JpaRepository<Room,Long> {

}
