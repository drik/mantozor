package com.dirks.cool.service;

import com.dirks.cool.service.dto.MantisImportDTO;
import java.util.List;

/**
 * Service Interface for managing MantisImport.
 */
public interface MantisImportService {

    /**
     * Save a mantisImport.
     *
     * @param mantisImportDTO the entity to save
     * @return the persisted entity
     */
    MantisImportDTO save(MantisImportDTO mantisImportDTO);

    /**
     * Get all the mantisImports.
     *
     * @return the list of entities
     */
    List<MantisImportDTO> findAll();

    /**
     * Get the "id" mantisImport.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MantisImportDTO findOne(Long id);

    /**
     * Delete the "id" mantisImport.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
