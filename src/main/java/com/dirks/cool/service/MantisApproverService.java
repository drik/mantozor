package com.dirks.cool.service;

import com.dirks.cool.domain.MantisApprover;
import com.dirks.cool.repository.MantisApproverRepository;
import com.dirks.cool.service.dto.MantisApproverDTO;
import com.dirks.cool.service.mapper.MantisApproverMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MantisApprover.
 */
@Service
@Transactional
public class MantisApproverService {

    private final Logger log = LoggerFactory.getLogger(MantisApproverService.class);

    private final MantisApproverRepository mantisApproverRepository;

    private final MantisApproverMapper mantisApproverMapper;

    public MantisApproverService(MantisApproverRepository mantisApproverRepository, MantisApproverMapper mantisApproverMapper) {
        this.mantisApproverRepository = mantisApproverRepository;
        this.mantisApproverMapper = mantisApproverMapper;
    }

    /**
     * Save a mantisApprover.
     *
     * @param mantisApproverDTO the entity to save
     * @return the persisted entity
     */
    public MantisApproverDTO save(MantisApproverDTO mantisApproverDTO) {
        log.debug("Request to save MantisApprover : {}", mantisApproverDTO);
        MantisApprover mantisApprover = mantisApproverMapper.toEntity(mantisApproverDTO);
        mantisApprover = mantisApproverRepository.save(mantisApprover);
        return mantisApproverMapper.toDto(mantisApprover);
    }

    /**
     * Get all the mantisApprovers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MantisApproverDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MantisApprovers");
        return mantisApproverRepository.findAll(pageable)
            .map(mantisApproverMapper::toDto);
    }

    /**
     * Get one mantisApprover by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MantisApproverDTO findOne(Long id) {
        log.debug("Request to get MantisApprover : {}", id);
        MantisApprover mantisApprover = mantisApproverRepository.findOne(id);
        return mantisApproverMapper.toDto(mantisApprover);
    }

    /**
     * Delete the mantisApprover by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MantisApprover : {}", id);
        mantisApproverRepository.delete(id);
    }
}
