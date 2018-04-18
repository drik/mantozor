package com.dirks.cool.service;

import com.dirks.cool.domain.MantisStatus;
import com.dirks.cool.service.dto.MantisStatusDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MantisStatus.
 */
public interface MantisStatusService {

    /**
     * Save a mantisStatus.
     *
     * @param mantisStatusDTO the entity to save
     * @return the persisted entity
     */
    MantisStatusDTO save(MantisStatusDTO mantisStatusDTO);

    /**
     * Get all the mantisStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MantisStatusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mantisStatus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MantisStatusDTO findOne(Long id);

    /**
     * Delete the "id" mantisStatus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    /**
     * Find all the status of a mantis.
     * 
     * @param mantisId
     * @return
     */
    List<MantisStatusDTO> findByMantisId(Long mantisId);
    
    /**
     * Get the last status of a mantis.
     * 
     * @param mantisId
     * @return
     */
    MantisStatusDTO findLastOneForMantis(Long mantisId);
}
