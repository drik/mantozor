package com.dirks.cool.repository;

import com.dirks.cool.domain.MantisFile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MantisFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MantisFileRepository extends JpaRepository<MantisFile, Long> {

}
