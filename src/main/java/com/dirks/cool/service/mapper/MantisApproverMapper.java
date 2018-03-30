package com.dirks.cool.service.mapper;

import com.dirks.cool.domain.*;
import com.dirks.cool.service.dto.MantisApproverDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MantisApprover and its DTO MantisApproverDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MantisApproverMapper extends EntityMapper<MantisApproverDTO, MantisApprover> {



    default MantisApprover fromId(Long id) {
        if (id == null) {
            return null;
        }
        MantisApprover mantisApprover = new MantisApprover();
        mantisApprover.setId(id);
        return mantisApprover;
    }
}
