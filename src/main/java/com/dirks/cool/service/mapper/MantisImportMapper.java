package com.dirks.cool.service.mapper;

import com.dirks.cool.domain.*;
import com.dirks.cool.service.dto.MantisImportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MantisImport and its DTO MantisImportDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface MantisImportMapper extends EntityMapper<MantisImportDTO, MantisImport> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user", target = "user")
    MantisImportDTO toDto(MantisImport mantisImport);

    @Mapping(source = "userId", target = "user")
    MantisImport toEntity(MantisImportDTO mantisImportDTO);

    default MantisImport fromId(Long id) {
        if (id == null) {
            return null;
        }
        MantisImport mantisImport = new MantisImport();
        mantisImport.setId(id);
        return mantisImport;
    }
}
