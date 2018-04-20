package com.dirks.cool.service.mapper;

import com.dirks.cool.domain.*;
import com.dirks.cool.service.dto.MantisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mantis and its DTO MantisDTO.
 */
@Mapper(componentModel = "spring", uses = {ProjectMapper.class, StateMapper.class})
public interface MantisMapper extends EntityMapper<MantisDTO, Mantis> {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "state.id", target = "stateId")
    MantisDTO toDto(Mantis mantis);
    
    @Mapping(source = "projectId", target = "project")
    @Mapping(source = "stateId", target = "state")
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
