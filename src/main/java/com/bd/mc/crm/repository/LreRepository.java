package com.bd.mc.crm.repository;

import com.bd.mc.crm.domain.Lre;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Lre entity.
 */
@SuppressWarnings("unused")
public interface LreRepository extends JpaRepository<Lre,Long> {

}
