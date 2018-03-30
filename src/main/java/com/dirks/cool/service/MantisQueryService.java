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

import com.dirks.cool.domain.Mantis;
import com.dirks.cool.domain.*; // for static metamodels
import com.dirks.cool.repository.MantisRepository;
import com.dirks.cool.service.dto.MantisCriteria;

import com.dirks.cool.service.dto.MantisDTO;
import com.dirks.cool.service.mapper.MantisMapper;

/**
 * Service for executing complex queries for Mantis entities in the database.
 * The main input is a {@link MantisCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MantisDTO} or a {@link Page} of {@link MantisDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MantisQueryService extends QueryService<Mantis> {

    private final Logger log = LoggerFactory.getLogger(MantisQueryService.class);


    private final MantisRepository mantisRepository;

    private final MantisMapper mantisMapper;

    public MantisQueryService(MantisRepository mantisRepository, MantisMapper mantisMapper) {
        this.mantisRepository = mantisRepository;
        this.mantisMapper = mantisMapper;
    }

    /**
     * Return a {@link List} of {@link MantisDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MantisDTO> findByCriteria(MantisCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Mantis> specification = createSpecification(criteria);
        return mantisMapper.toDto(mantisRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MantisDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MantisDTO> findByCriteria(MantisCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Mantis> specification = createSpecification(criteria);
        final Page<Mantis> result = mantisRepository.findAll(specification, page);
        return result.map(mantisMapper::toDto);
    }

    /**
     * Function to convert MantisCriteria to a {@link Specifications}
     */
    private Specifications<Mantis> createSpecification(MantisCriteria criteria) {
        Specifications<Mantis> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Mantis_.id));
            }
            if (criteria.getMantisNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMantisNumber(), Mantis_.mantisNumber));
            }
            if (criteria.getSubmissionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubmissionDate(), Mantis_.submissionDate));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getProjectId(), Mantis_.project, Project_.id));
            }
        }
        return specification;
    }

}
