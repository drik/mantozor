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

import com.dirks.cool.domain.Status;
import com.dirks.cool.domain.*; // for static metamodels
import com.dirks.cool.repository.StatusRepository;
import com.dirks.cool.service.dto.StatusCriteria;

import com.dirks.cool.service.dto.StatusDTO;
import com.dirks.cool.service.mapper.StatusMapper;

/**
 * Service for executing complex queries for Status entities in the database.
 * The main input is a {@link StatusCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StatusDTO} or a {@link Page} of {@link StatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StatusQueryService extends QueryService<Status> {

    private final Logger log = LoggerFactory.getLogger(StatusQueryService.class);


    private final StatusRepository statusRepository;

    private final StatusMapper statusMapper;

    public StatusQueryService(StatusRepository statusRepository, StatusMapper statusMapper) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    /**
     * Return a {@link List} of {@link StatusDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StatusDTO> findByCriteria(StatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Status> specification = createSpecification(criteria);
        return statusMapper.toDto(statusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StatusDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StatusDTO> findByCriteria(StatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Status> specification = createSpecification(criteria);
        final Page<Status> result = statusRepository.findAll(specification, page);
        return result.map(statusMapper::toDto);
    }

    /**
     * Function to convert StatusCriteria to a {@link Specifications}
     */
    private Specifications<Status> createSpecification(StatusCriteria criteria) {
        Specifications<Status> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Status_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Status_.name));
            }
        }
        return specification;
    }

}
