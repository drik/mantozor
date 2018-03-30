package com.dirks.cool.repository;

import com.dirks.cool.domain.MantisImport;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the MantisImport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MantisImportRepository extends JpaRepository<MantisImport, Long>, JpaSpecificationExecutor<MantisImport> {

    @Query("select mantis_import from MantisImport mantis_import where mantis_import.user.login = ?#{principal.username}")
    List<MantisImport> findByUserIsCurrentUser();

}
