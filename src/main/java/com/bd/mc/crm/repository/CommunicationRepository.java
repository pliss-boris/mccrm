package com.bd.mc.crm.repository;

import com.bd.mc.crm.domain.Communication;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Communication entity.
 */
@SuppressWarnings("unused")
public interface CommunicationRepository extends JpaRepository<Communication,Long> {

}
