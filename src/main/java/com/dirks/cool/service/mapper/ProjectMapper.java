package com.dirks.cool.service.mapper;

import com.dirks.cool.domain.*;
import com.dirks.cool.service.dto.ProjectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Project and its DTO ProjectDTO.
 */
@Mapper(componentModel = "spring", uses = {ReferentMapper.class})
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {

    @Mapping(source = "referent.id", target = "referentId")
    ProjectDTO toDto(Project project);

    @Mapping(source = "referentId", target = "referent")
    Project toEntity(ProjectDTO projectDTO);

    default Project fromId(Long id) {
        if (id == null) {
            return null;
        }
        Project project = new Project();
        project.setId(id);
        return project;
    }
}
