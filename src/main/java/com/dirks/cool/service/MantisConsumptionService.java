package com.dirks.cool.service;

import com.dirks.cool.service.dto.MantisConsumptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MantisConsumption.
 */
public interface MantisConsumptionService {

    /**
     * Save a mantisConsumption.
     *
     * @param mantisConsumptionDTO the entity to save
     * @return the persisted entity
     */
    MantisConsumptionDTO save(MantisConsumptionDTO mantisConsumptionDTO);

    /**
     * Get all the mantisConsumptions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MantisConsumptionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mantisConsumption.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MantisConsumptionDTO findOne(Long id);

    /**
     * Delete the "id" mantisConsumption.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
