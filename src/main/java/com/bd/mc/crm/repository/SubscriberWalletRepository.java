package com.bd.mc.crm.repository;

import com.bd.mc.crm.domain.SubscriberWallet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SubscriberWallet entity.
 */
@SuppressWarnings("unused")
public interface SubscriberWalletRepository extends JpaRepository<SubscriberWallet,Long> {

}
