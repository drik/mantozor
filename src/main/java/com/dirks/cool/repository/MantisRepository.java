package com.dirks.cool.repository;

import com.dirks.cool.domain.Mantis;
import com.dirks.cool.domain.Referent;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Mantis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MantisRepository extends JpaRepository<Mantis, Long>, JpaSpecificationExecutor<Mantis> {
	Mantis findByMantisNumber(String mantisNumber);
}
