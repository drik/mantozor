package com.dirks.cool.repository;

import com.dirks.cool.domain.MantisStatus;
import com.dirks.cool.domain.Referent;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Referent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferentRepository extends JpaRepository<Referent, Long>, JpaSpecificationExecutor<Referent> {
	Referent findByName(String name);

    @Query("select referent from Referent referent where referent.user.login = ?#{principal.username}")
    Referent findByUserIsCurrentUser();
}
