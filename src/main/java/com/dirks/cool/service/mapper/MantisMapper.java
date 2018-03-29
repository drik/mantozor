package com.dirks.cool.service.mapper;

import com.dirks.cool.domain.*;
import com.dirks.cool.service.dto.MantisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mantis and its DTO MantisDTO.
 */
@Mapper(componentModel = "spring", uses = {ProjectMapper.class, ReferentMapper.class})
public interface MantisMapper extends EntityMapper<MantisDTO, Mantis> {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "referent.id", target = "referentId")
    MantisDTO toDto(Mantis mantis);

    @Mapping(source = "projectId", target = "project")
    @Mapping(source = "referentId", target = "referent")
    Mantis toEntity(MantisDTO mantisDTO);

    default Mantis fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mantis mantis = new Mantis();
        mantis.setId(id);
        return mantis;
    }
}
