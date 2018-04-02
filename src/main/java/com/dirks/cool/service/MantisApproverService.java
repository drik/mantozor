package com.dirks.cool.service;

import com.dirks.cool.service.dto.MantisApproverDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MantisApprover.
 */
public interface MantisApproverService {

    /**
     * Save a mantisApprover.
     *
     * @param mantisApproverDTO the entity to save
     * @return the persisted entity
     */
    MantisApproverDTO save(MantisApproverDTO mantisApproverDTO);

    /**
     * Get all the mantisApprovers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MantisApproverDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mantisApprover.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MantisApproverDTO findOne(Long id);

    /**
     * Delete the "id" mantisApprover.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
