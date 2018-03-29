package com.dirks.cool.repository;

import com.dirks.cool.domain.MantisStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MantisStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MantisStatusRepository extends JpaRepository<MantisStatus, Long> {

}
