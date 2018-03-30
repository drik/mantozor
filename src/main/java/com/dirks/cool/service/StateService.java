package com.dirks.cool.service;

import com.dirks.cool.domain.State;
import com.dirks.cool.repository.StateRepository;
import com.dirks.cool.service.dto.StateDTO;
import com.dirks.cool.service.mapper.StateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing State.
 */
@Service
@Transactional
public class StateService {

    private final Logger log = LoggerFactory.getLogger(StateService.class);

    private final StateRepository stateRepository;

    private final StateMapper stateMapper;

    public StateService(StateRepository stateRepository, StateMapper stateMapper) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
    }

    /**
     * Save a state.
     *
     * @param stateDTO the entity to save
     * @return the persisted entity
     */
    public StateDTO save(StateDTO stateDTO) {
        log.debug("Request to save State : {}", stateDTO);
        State state = stateMapper.toEntity(stateDTO);
        state = stateRepository.save(state);
        return stateMapper.toDto(state);
    }

    /**
     * Get all the states.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all States");
        return stateRepository.findAll(pageable)
            .map(stateMapper::toDto);
    }

    /**
     * Get one state by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public StateDTO findOne(Long id) {
        log.debug("Request to get State : {}", id);
        State state = stateRepository.findOne(id);
        return stateMapper.toDto(state);
    }

    /**
     * Delete the state by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete State : {}", id);
        stateRepository.delete(id);
    }
}
