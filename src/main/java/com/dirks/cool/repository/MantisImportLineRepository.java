package com.dirks.cool.repository;

import com.dirks.cool.domain.MantisImportLine;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MantisImportLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MantisImportLineRepository extends JpaRepository<MantisImportLine, Long>, JpaSpecificationExecutor<MantisImportLine> {

}
