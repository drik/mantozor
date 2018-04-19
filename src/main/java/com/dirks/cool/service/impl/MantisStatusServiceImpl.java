package com.dirks.cool.service.impl;

import com.dirks.cool.service.MantisStatusService;
import com.dirks.cool.service.UserService;
import com.dirks.cool.domain.MantisStatus;
import com.dirks.cool.domain.Status;
import com.dirks.cool.repository.MantisStatusRepository;
import com.dirks.cool.repository.StatusRepository;
import com.dirks.cool.service.dto.MantisStatusDTO;
import com.dirks.cool.service.mapper.MantisStatusMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MantisStatus.
 */
@Service
@Transactional
public class MantisStatusServiceImpl implements MantisStatusService {

    private final Logger log = LoggerFactory.getLogger(MantisStatusServiceImpl.class);

    private final StatusRepository statusRepository;
    
    private final MantisStatusRepository mantisStatusRepository;
    
    private final UserService userService;

    private final MantisStatusMapper mantisStatusMapper;

    public MantisStatusServiceImpl(MantisStatusRepository mantisStatusRepository, MantisStatusMapper mantisStatusMapper, UserService userService, StatusRepository statusRepository) {
        this.mantisStatusRepository = mantisStatusRepository;
        this.mantisStatusMapper = mantisStatusMapper;
        this.userService = userService;
        this.statusRepository = statusRepository;
    }

    /**
     * Save a mantisStatus.
     *
     * @param mantisStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MantisStatusDTO save(MantisStatusDTO mantisStatusDTO) {
        log.debug("Request to save MantisStatus : {}", mantisStatusDTO);
        MantisStatus mantisStatus = mantisStatusMapper.toEntity(mantisStatusDTO);
        if(mantisStatus.getStatus() == null) {
        	throw new RuntimeException("Status must be filled in");
        }
        mantisStatus.setComments(mantisStatusDTO.getComments());
        Status selectedStatus = statusRepository.findOne(mantisStatus.getStatus().getId());
        if(selectedStatus.isMandatoryApprover() && mantisStatus.getApprover() == null) {
        	throw new RuntimeException("Approver needed for this status");
        }
        mantisStatus.setUser(userService.getUserWithAuthorities().get());
        mantisStatus = mantisStatusRepository.save(mantisStatus);
        return mantisStatusMapper.toDto(mantisStatus);
    }

    /**
     * Get all the mantisStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MantisStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MantisStatuses");
        return mantisStatusRepository.findAll(pageable)
            .map(mantisStatusMapper::toDto);
    }

    /**
     * Get one mantisStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MantisStatusDTO findOne(Long id) {
        log.debug("Request to get MantisStatus : {}", id);
        MantisStatus mantisStatus = mantisStatusRepository.findOne(id);
        return mantisStatusMapper.toDto(mantisStatus);
    }

    /**
     * Delete the mantisStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MantisStatus : {}", id);
        mantisStatusRepository.delete(id);
    }

	@Override
	public List<MantisStatusDTO> findByMantisId(Long mantisId) {
		log.debug("Request to get all MantisStatuses for a mantis : " + mantisId);
        return mantisStatusMapper.toDto(mantisStatusRepository.findByMantisId(mantisId));
	}

	@Override
	public MantisStatusDTO findLastOneForMantis(Long mantisId) {
		List<MantisStatusDTO> mantisStatuses = this.findByMantisId(mantisId);
		if(mantisStatuses.isEmpty()) {
			return null;
		}
		return mantisStatuses.get(mantisStatuses.size() - 1);
	}
}
