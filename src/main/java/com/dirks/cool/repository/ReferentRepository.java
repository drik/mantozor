package com.dirks.cool.repository;

import com.dirks.cool.domain.Referent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Referent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferentRepository extends JpaRepository<Referent, Long> {

}
