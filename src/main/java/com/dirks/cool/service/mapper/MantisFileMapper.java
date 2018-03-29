package com.dirks.cool.service.mapper;

import com.dirks.cool.domain.*;
import com.dirks.cool.service.dto.MantisFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MantisFile and its DTO MantisFileDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MantisFileMapper extends EntityMapper<MantisFileDTO, MantisFile> {



    default MantisFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        MantisFile mantisFile = new MantisFile();
        mantisFile.setId(id);
        return mantisFile;
    }
}
