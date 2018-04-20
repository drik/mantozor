package com.dirks.cool.service;

import com.dirks.cool.domain.MantisConsumption;
import com.dirks.cool.service.dto.MantisConsumptionDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    
    /**
     * Find all consumption for a mantis.
     * 
     * @param mantisId
     * @return
     */
    List<MantisConsumption> findByMantisId(Long mantisId);
    
    /**
     * calculateTotalConsumptionConsumed.
     * 
     * @param mantisId
     * @return
     */
    Double calculateTotalConsumptionConsumed(@Param("mantisId") Long mantisId);
    
    /**
     * calculateTotalConsumptionToBill.
     * 
     * @param mantisId
     * @return
     */
    Double calculateTotalConsumptionToBill(@Param("mantisId") Long mantisId);
}
