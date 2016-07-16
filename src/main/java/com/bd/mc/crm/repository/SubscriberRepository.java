package com.bd.mc.crm.repository;

import com.bd.mc.crm.domain.Subscriber;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Subscriber entity.
 */
@SuppressWarnings("unused")
public interface SubscriberRepository extends JpaRepository<Subscriber,Long> {

}
