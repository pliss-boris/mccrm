package com.bd.mc.crm.repository;

import com.bd.mc.crm.domain.SubscriberWalletProperty;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SubscriberWalletProperty entity.
 */
@SuppressWarnings("unused")
public interface SubscriberWalletPropertyRepository extends JpaRepository<SubscriberWalletProperty,Long> {

}
