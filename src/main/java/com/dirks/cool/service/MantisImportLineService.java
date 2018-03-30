package com.dirks.cool.service;

import com.dirks.cool.service.dto.MantisImportLineDTO;
import java.util.List;

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
     * @return the list of entities
     */
    List<MantisImportLineDTO> findAll();

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
