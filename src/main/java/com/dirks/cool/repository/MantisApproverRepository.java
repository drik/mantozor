package com.dirks.cool.repository;

import com.dirks.cool.domain.MantisApprover;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MantisApprover entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MantisApproverRepository extends JpaRepository<MantisApprover, Long> {

}
