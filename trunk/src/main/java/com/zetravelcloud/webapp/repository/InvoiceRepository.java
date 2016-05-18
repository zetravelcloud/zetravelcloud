package com.zetravelcloud.webapp.repository;

import com.zetravelcloud.webapp.domain.Invoice;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Invoice entity.
 */
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

}
