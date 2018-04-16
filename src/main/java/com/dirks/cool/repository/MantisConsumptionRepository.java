package com.dirks.cool.repository;

import com.dirks.cool.domain.MantisConsumption;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the MantisConsumption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MantisConsumptionRepository extends JpaRepository<MantisConsumption, Long>, JpaSpecificationExecutor<MantisConsumption> {

    @Query("select mantis_consumption from MantisConsumption mantis_consumption where mantis_consumption.user.login = ?#{principal.username}")
    List<MantisConsumption> findByUserIsCurrentUser();

}
