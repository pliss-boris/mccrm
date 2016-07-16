package com.bd.mc.crm.repository;

import com.bd.mc.crm.domain.ClassOfService;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClassOfService entity.
 */
@SuppressWarnings("unused")
public interface ClassOfServiceRepository extends JpaRepository<ClassOfService,Long> {

}
