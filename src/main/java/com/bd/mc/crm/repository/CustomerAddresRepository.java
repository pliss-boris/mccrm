package com.bd.mc.crm.repository;

import com.bd.mc.crm.domain.CustomerAddres;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerAddres entity.
 */
@SuppressWarnings("unused")
public interface CustomerAddresRepository extends JpaRepository<CustomerAddres,Long> {
    @Query("select o from CustomerAddres o where o.customer.id = :customerId")
    List<CustomerAddres> findByCustomerId(@Param("customerId") Long customerId);

}
