package com.bd.mc.crm.repository;

import com.bd.mc.crm.domain.CustomerAddres;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerAddres entity.
 */
@SuppressWarnings("unused")
public interface CustomerAddresRepository extends JpaRepository<CustomerAddres,Long> {

}
