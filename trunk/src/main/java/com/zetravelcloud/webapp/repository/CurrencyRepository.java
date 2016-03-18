package com.zetravelcloud.webapp.repository;

import com.zetravelcloud.webapp.domain.Currency;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Currency entity.
 */
public interface CurrencyRepository extends JpaRepository<Currency,Long> {

}
