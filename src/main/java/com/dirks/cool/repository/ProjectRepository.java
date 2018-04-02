package com.dirks.cool.repository;

import com.dirks.cool.domain.Project;
import com.dirks.cool.domain.Referent;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
	Project findByName(String name);
}
