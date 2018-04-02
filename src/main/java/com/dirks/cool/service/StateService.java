package com.dirks.cool.service;

import com.dirks.cool.service.dto.StateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing State.
 */
public interface StateService {

    /**
     * Save a state.
     *
     * @param stateDTO the entity to save
     * @return the persisted entity
     */
    StateDTO save(StateDTO stateDTO);

    /**
     * Get all the states.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" state.
     *
     * @param id the id of the entity
     * @return the entity
     */
    StateDTO findOne(Long id);

    /**
     * Delete the "id" state.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
