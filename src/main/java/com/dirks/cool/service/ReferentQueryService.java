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

import com.dirks.cool.domain.Referent;
import com.dirks.cool.domain.*; // for static metamodels
import com.dirks.cool.repository.ReferentRepository;
import com.dirks.cool.service.dto.ReferentCriteria;

import com.dirks.cool.service.dto.ReferentDTO;
import com.dirks.cool.service.mapper.ReferentMapper;

/**
 * Service for executing complex queries for Referent entities in the database.
 * The main input is a {@link ReferentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReferentDTO} or a {@link Page} of {@link ReferentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReferentQueryService extends QueryService<Referent> {

    private final Logger log = LoggerFactory.getLogger(ReferentQueryService.class);


    private final ReferentRepository referentRepository;

    private final ReferentMapper referentMapper;

    public ReferentQueryService(ReferentRepository referentRepository, ReferentMapper referentMapper) {
        this.referentRepository = referentRepository;
        this.referentMapper = referentMapper;
    }

    /**
     * Return a {@link List} of {@link ReferentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReferentDTO> findByCriteria(ReferentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Referent> specification = createSpecification(criteria);
        return referentMapper.toDto(referentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReferentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReferentDTO> findByCriteria(ReferentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Referent> specification = createSpecification(criteria);
        final Page<Referent> result = referentRepository.findAll(specification, page);
        return result.map(referentMapper::toDto);
    }

    /**
     * Function to convert ReferentCriteria to a {@link Specifications}
     */
    private Specifications<Referent> createSpecification(ReferentCriteria criteria) {
        Specifications<Referent> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Referent_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Referent_.name));
            }
        }
        return specification;
    }

}
