package com.dirks.cool.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.dirks.cool.domain.State;
import com.dirks.cool.domain.*; // for static metamodels
import com.dirks.cool.repository.StateRepository;
import com.dirks.cool.service.dto.StateCriteria;

import com.dirks.cool.service.dto.StateDTO;
import com.dirks.cool.service.mapper.StateMapper;

/**
 * Service for executing complex queries for State entities in the database.
 * The main input is a {@link StateCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StateDTO} or a {@link Page} of {@link StateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StateQueryService extends QueryService<State> {

    private final Logger log = LoggerFactory.getLogger(StateQueryService.class);


    private final StateRepository stateRepository;

    private final StateMapper stateMapper;

    public StateQueryService(StateRepository stateRepository, StateMapper stateMapper) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
    }

    /**
     * Return a {@link List} of {@link StateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StateDTO> findByCriteria(StateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<State> specification = createSpecification(criteria);
        return stateMapper.toDto(stateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StateDTO> findByCriteria(StateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<State> specification = createSpecification(criteria);
        final Page<State> result = stateRepository.findAll(specification, page);
        return result.map(stateMapper::toDto);
    }

    /**
     * Function to convert StateCriteria to a {@link Specifications}
     */
    private Specifications<State> createSpecification(StateCriteria criteria) {
        Specifications<State> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), State_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), State_.name));
            }
        }
        return specification;
    }

}
