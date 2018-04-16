package com.dirks.cool.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.dirks.cool.domain.MantisConsumption;
import com.dirks.cool.domain.*; // for static metamodels
import com.dirks.cool.repository.MantisConsumptionRepository;
import com.dirks.cool.service.dto.MantisConsumptionCriteria;

import com.dirks.cool.service.dto.MantisConsumptionDTO;
import com.dirks.cool.service.mapper.MantisConsumptionMapper;

/**
 * Service for executing complex queries for MantisConsumption entities in the database.
 * The main input is a {@link MantisConsumptionCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MantisConsumptionDTO} or a {@link Page} of {@link MantisConsumptionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MantisConsumptionQueryService extends QueryService<MantisConsumption> {

    private final Logger log = LoggerFactory.getLogger(MantisConsumptionQueryService.class);


    private final MantisConsumptionRepository mantisConsumptionRepository;

    private final MantisConsumptionMapper mantisConsumptionMapper;

    public MantisConsumptionQueryService(MantisConsumptionRepository mantisConsumptionRepository, MantisConsumptionMapper mantisConsumptionMapper) {
        this.mantisConsumptionRepository = mantisConsumptionRepository;
        this.mantisConsumptionMapper = mantisConsumptionMapper;
    }

    /**
     * Return a {@link List} of {@link MantisConsumptionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MantisConsumptionDTO> findByCriteria(MantisConsumptionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<MantisConsumption> specification = createSpecification(criteria);
        return mantisConsumptionMapper.toDto(mantisConsumptionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MantisConsumptionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MantisConsumptionDTO> findByCriteria(MantisConsumptionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<MantisConsumption> specification = createSpecification(criteria);
        final Page<MantisConsumption> result = mantisConsumptionRepository.findAll(specification, page);
        return result.map(mantisConsumptionMapper::toDto);
    }

    /**
     * Function to convert MantisConsumptionCriteria to a {@link Specifications}
     */
    private Specifications<MantisConsumption> createSpecification(MantisConsumptionCriteria criteria) {
        Specifications<MantisConsumption> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MantisConsumption_.id));
            }
            if (criteria.getSubmissionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubmissionDate(), MantisConsumption_.submissionDate));
            }
            if (criteria.getConsumed() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConsumed(), MantisConsumption_.consumed));
            }
            if (criteria.getToBill() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToBill(), MantisConsumption_.toBill));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), MantisConsumption_.user, User_.id));
            }
            if (criteria.getMantisId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMantisId(), MantisConsumption_.mantis, Mantis_.id));
            }
        }
        return specification;
    }

}
