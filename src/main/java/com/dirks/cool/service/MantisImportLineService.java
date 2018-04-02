package com.dirks.cool.service;

import com.dirks.cool.service.dto.MantisImportLineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MantisImportLine.
 */
public interface MantisImportLineService {

    /**
     * Save a mantisImportLine.
     *
     * @param mantisImportLineDTO the entity to save
     * @return the persisted entity
     */
    MantisImportLineDTO save(MantisImportLineDTO mantisImportLineDTO);

    /**
     * Get all the mantisImportLines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MantisImportLineDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mantisImportLine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MantisImportLineDTO findOne(Long id);

    /**
     * Delete the "id" mantisImportLine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
