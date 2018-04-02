package com.dirks.cool.service;

import com.dirks.cool.service.dto.MantisImportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MantisImportDTO> findAll(Pageable pageable);

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

	MantisImportDTO importMantis(MantisImportDTO mantisImportDTO);
}
