package com.dirks.cool.service.mapper;

import com.dirks.cool.domain.*;
import com.dirks.cool.service.dto.MantisStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MantisStatus and its DTO MantisStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {MantisMapper.class, StatusMapper.class, UserMapper.class, MantisApproverMapper.class})
public interface MantisStatusMapper extends EntityMapper<MantisStatusDTO, MantisStatus> {

    @Mapping(source = "mantis.id", target = "mantisId")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "approver.id", target = "approverId")
    @Mapping(source = "comments", target = "comments")
    MantisStatusDTO toDto(MantisStatus mantisStatus);

    @Mapping(source = "mantisId", target = "mantis")    
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "approverId", target = "approver")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "comments", target = "comments")
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
