package com.dirks.cool.service.mapper;

import com.dirks.cool.domain.*;
import com.dirks.cool.service.dto.ReferentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Referent and its DTO ReferentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReferentMapper extends EntityMapper<ReferentDTO, Referent> {



    default Referent fromId(Long id) {
        if (id == null) {
            return null;
        }
        Referent referent = new Referent();
        referent.setId(id);
        return referent;
    }
}
