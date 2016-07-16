package com.bd.mc.crm.repository;

import com.bd.mc.crm.domain.Remark;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Remark entity.
 */
@SuppressWarnings("unused")
public interface RemarkRepository extends JpaRepository<Remark,Long> {

}
