package com.dirks.cool.service;

import com.dirks.cool.service.dto.MantisDTO;
import com.dirks.cool.service.dto.StatusStateStatsDTO;
import com.dirks.cool.service.dto.StatusStatsDTO;
import com.dirks.cool.service.dto.TimelineDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Mantis.
 */
public interface MantisService {

    /**
     * Save a mantis.
     *
     * @param mantisDTO the entity to save
     * @return the persisted entity
     */
    MantisDTO save(MantisDTO mantisDTO);

    /**
     * Get all the mantis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MantisDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mantis.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MantisDTO findOne(Long id);

    /**
     * Delete the "id" mantis.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    /**
     * Get stats for status.
     * 
     * @return
     */
    List<StatusStatsDTO> getStatsForStatus();
    
    /**
     * Get stats for status and state.
     * 
     * @return
     */
    List<StatusStateStatsDTO> getStatsForStatusAndState();
    
    /**
     * Get timeline events for a mantis.
     * @param mantisId
     * @return
     */
    List<TimelineDTO> getTimelineEvents(Long mantisId);
}
