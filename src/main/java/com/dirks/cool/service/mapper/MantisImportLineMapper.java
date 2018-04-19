package com.dirks.cool.service.mapper;

import com.dirks.cool.domain.*;
import com.dirks.cool.service.dto.MantisImportLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MantisImportLine and its DTO MantisImportLineDTO.
 */
@Mapper(componentModel = "spring", uses = {StateMapper.class, MantisImportMapper.class, MantisMapper.class})
public interface MantisImportLineMapper extends EntityMapper<MantisImportLineDTO, MantisImportLine> {

    @Mapping(source = "state.id", target = "stateId")
    @Mapping(source = "mantisImport.id", target = "mantisImportId")
    @Mapping(source = "mantis.id", target = "mantisId")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "mantisImport", target = "mantisImport")
    @Mapping(source = "mantis", target = "mantis")
    MantisImportLineDTO toDto(MantisImportLine mantisImportLine);

    @Mapping(source = "state", target = "state")
    @Mapping(source = "mantisImport", target = "mantisImport")
    @Mapping(source = "mantis", target = "mantis")
    MantisImportLine toEntity(MantisImportLineDTO mantisImportLineDTO);

    default MantisImportLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MantisImportLine mantisImportLine = new MantisImportLine();
        mantisImportLine.setId(id);
        return mantisImportLine;
    }
}
