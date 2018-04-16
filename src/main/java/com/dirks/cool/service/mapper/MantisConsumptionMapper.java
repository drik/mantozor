package com.dirks.cool.service.mapper;

import com.dirks.cool.domain.*;
import com.dirks.cool.service.dto.MantisConsumptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MantisConsumption and its DTO MantisConsumptionDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, MantisMapper.class})
public interface MantisConsumptionMapper extends EntityMapper<MantisConsumptionDTO, MantisConsumption> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "mantis.id", target = "mantisId")
    MantisConsumptionDTO toDto(MantisConsumption mantisConsumption);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "mantisId", target = "mantis")
    MantisConsumption toEntity(MantisConsumptionDTO mantisConsumptionDTO);

    default MantisConsumption fromId(Long id) {
        if (id == null) {
            return null;
        }
        MantisConsumption mantisConsumption = new MantisConsumption();
        mantisConsumption.setId(id);
        return mantisConsumption;
    }
}
