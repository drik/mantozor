package com.dirks.cool.service;

import com.dirks.cool.service.dto.MantisFileDTO;
import java.util.List;

/**
 * Service Interface for managing MantisFile.
 */
public interface MantisFileService {

    /**
     * Save a mantisFile.
     *
     * @param mantisFileDTO the entity to save
     * @return the persisted entity
     */
    MantisFileDTO save(MantisFileDTO mantisFileDTO);

    /**
     * Get all the mantisFiles.
     *
     * @return the list of entities
     */
    List<MantisFileDTO> findAll();

    /**
     * Get the "id" mantisFile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MantisFileDTO findOne(Long id);

    /**
     * Delete the "id" mantisFile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
