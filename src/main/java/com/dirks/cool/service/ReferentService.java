package com.dirks.cool.service;

import com.dirks.cool.domain.Referent;
import com.dirks.cool.service.dto.ReferentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Referent.
 */
public interface ReferentService {

    /**
     * Save a referent.
     *
     * @param referentDTO the entity to save
     * @return the persisted entity
     */
    ReferentDTO save(ReferentDTO referentDTO);

    /**
     * Get all the referents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ReferentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" referent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ReferentDTO findOne(Long id);

    /**
     * Delete the "id" referent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    /**
     * Find Referent associeted with current connected user.
     * 
     * @return
     */
    ReferentDTO findByUserIsCurrentUser();
}
