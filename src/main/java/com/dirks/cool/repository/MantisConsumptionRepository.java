package com.dirks.cool.repository;

import com.dirks.cool.domain.MantisConsumption;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MantisConsumption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MantisConsumptionRepository extends JpaRepository<MantisConsumption, Long>, JpaSpecificationExecutor<MantisConsumption> {

    @Query("select mantis_consumption from MantisConsumption mantis_consumption where mantis_consumption.user.login = ?#{principal.username}")
    List<MantisConsumption> findByUserIsCurrentUser();

    List<MantisConsumption> findByMantisId(Long mantisId);
    
    @Query("select SUM(mc.consumed) from MantisConsumption mc where mc.mantis.id = :#{#mantisId}")
    Double calculateTotalConsumptionConsumed(@Param("mantisId") Long mantisId);
    
    @Query("select SUM(mc.toBill) from MantisConsumption mc where mc.mantis.id = :#{#mantisId}")
    Double calculateTotalConsumptionToBill(@Param("mantisId") Long mantisId);
}
