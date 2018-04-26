package com.dirks.cool.service.mapper;

import com.dirks.cool.domain.*;
import com.dirks.cool.service.dto.ReferentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Referent and its DTO ReferentDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ReferentMapper extends EntityMapper<ReferentDTO, Referent> {

    @Mapping(source = "user.id", target = "userId")
	ReferentDTO toDto(Referent referent);

    
    @Mapping(source = "userId", target = "user")
    Referent toEntity(ReferentDTO referentDTO);


    default Referent fromId(Long id) {
        if (id == null) {
            return null;
        }
        Referent referent = new Referent();
        referent.setId(id);
        return referent;
    }
}
