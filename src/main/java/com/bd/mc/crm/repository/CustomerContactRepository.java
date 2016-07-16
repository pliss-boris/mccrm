package com.bd.mc.crm.repository;

import com.bd.mc.crm.domain.CustomerContact;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerContact entity.
 */
@SuppressWarnings("unused")
public interface CustomerContactRepository extends JpaRepository<CustomerContact,Long> {

}
