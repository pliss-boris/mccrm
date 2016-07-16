package com.bd.mc.crm.repository;

import com.bd.mc.crm.domain.Customer;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("select o from Customer o where o.id <> :id")
    List<Customer> findCustomersExcludeCurrent(@Param("id") Long id);

}
