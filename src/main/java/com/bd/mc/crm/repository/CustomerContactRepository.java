package com.bd.mc.crm.repository;

import com.bd.mc.crm.domain.CustomerContact;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerContact entity.
 */
@SuppressWarnings("unused")
public interface CustomerContactRepository extends JpaRepository<CustomerContact,Long> {
    @Query("select o from CustomerContact o where o.customer.id = :customerId")
    List<CustomerContact> findCustomerContactByCustomerId(@Param("customerId") Long customerId);
}
