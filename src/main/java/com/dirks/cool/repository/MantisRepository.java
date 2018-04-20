package com.dirks.cool.repository;

import com.dirks.cool.domain.Mantis;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Mantis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MantisRepository extends JpaRepository<Mantis, Long>, JpaSpecificationExecutor<Mantis> {
	Mantis findByMantisNumber(String mantisNumber);
	
	@Query("select COUNT(mantis.id) from Mantis mantis where mantis.state.id = :#{#stateId}")
	Long countMantisState(@Param("stateId") Long stateId);
	
	@Query("select COUNT(mantis.id) from Mantis mantis where mantis.status.id = :#{#statusId}")
	Long countMantisStatus(@Param("statusId") Long statusId);
	
	@Query("select COUNT(mantis.id) from Mantis mantis where mantis.status.id = :#{#statusId} and mantis.state.id = :#{#stateId}")
	Long countMantis(@Param("statusId") Long statusId, @Param("stateId") Long stateId);
}
