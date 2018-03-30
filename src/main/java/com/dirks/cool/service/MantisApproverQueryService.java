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

import com.dirks.cool.domain.MantisApprover;
import com.dirks.cool.domain.*; // for static metamodels
import com.dirks.cool.repository.MantisApproverRepository;
import com.dirks.cool.service.dto.MantisApproverCriteria;

import com.dirks.cool.service.dto.MantisApproverDTO;
import com.dirks.cool.service.mapper.MantisApproverMapper;

/**
 * Service for executing complex queries for MantisApprover entities in the database.
 * The main input is a {@link MantisApproverCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MantisApproverDTO} or a {@link Page} of {@link MantisApproverDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MantisApproverQueryService extends QueryService<MantisApprover> {

    private final Logger log = LoggerFactory.getLogger(MantisApproverQueryService.class);


    private final MantisApproverRepository mantisApproverRepository;

    private final MantisApproverMapper mantisApproverMapper;

    public MantisApproverQueryService(MantisApproverRepository mantisApproverRepository, MantisApproverMapper mantisApproverMapper) {
        this.mantisApproverRepository = mantisApproverRepository;
        this.mantisApproverMapper = mantisApproverMapper;
    }

    /**
     * Return a {@link List} of {@link MantisApproverDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MantisApproverDTO> findByCriteria(MantisApproverCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<MantisApprover> specification = createSpecification(criteria);
        return mantisApproverMapper.toDto(mantisApproverRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MantisApproverDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MantisApproverDTO> findByCriteria(MantisApproverCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<MantisApprover> specification = createSpecification(criteria);
        final Page<MantisApprover> result = mantisApproverRepository.findAll(specification, page);
        return result.map(mantisApproverMapper::toDto);
    }

    /**
     * Function to convert MantisApproverCriteria to a {@link Specifications}
     */
    private Specifications<MantisApprover> createSpecification(MantisApproverCriteria criteria) {
        Specifications<MantisApprover> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MantisApprover_.id));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), MantisApprover_.fullName));
            }
        }
        return specification;
    }

}
