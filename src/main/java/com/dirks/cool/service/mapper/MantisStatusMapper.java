package com.dirks.cool.service.mapper;

import com.dirks.cool.domain.*;
import com.dirks.cool.service.dto.MantisStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MantisStatus and its DTO MantisStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {MantisMapper.class})
public interface MantisStatusMapper extends EntityMapper<MantisStatusDTO, MantisStatus> {

    @Mapping(source = "mantis.id", target = "mantisId")
    MantisStatusDTO toDto(MantisStatus mantisStatus);

    @Mapping(source = "mantisId", target = "mantis")
    MantisStatus toEntity(MantisStatusDTO mantisStatusDTO);

    default MantisStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        MantisStatus mantisStatus = new MantisStatus();
        mantisStatus.setId(id);
        return mantisStatus;
    }
}
