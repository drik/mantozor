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

import com.dirks.cool.domain.MantisStatus;
import com.dirks.cool.domain.*; // for static metamodels
import com.dirks.cool.repository.MantisStatusRepository;
import com.dirks.cool.service.dto.MantisStatusCriteria;

import com.dirks.cool.service.dto.MantisStatusDTO;
import com.dirks.cool.service.mapper.MantisStatusMapper;

/**
 * Service for executing complex queries for MantisStatus entities in the database.
 * The main input is a {@link MantisStatusCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MantisStatusDTO} or a {@link Page} of {@link MantisStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MantisStatusQueryService extends QueryService<MantisStatus> {

    private final Logger log = LoggerFactory.getLogger(MantisStatusQueryService.class);


    private final MantisStatusRepository mantisStatusRepository;

    private final MantisStatusMapper mantisStatusMapper;

    public MantisStatusQueryService(MantisStatusRepository mantisStatusRepository, MantisStatusMapper mantisStatusMapper) {
        this.mantisStatusRepository = mantisStatusRepository;
        this.mantisStatusMapper = mantisStatusMapper;
    }

    /**
     * Return a {@link List} of {@link MantisStatusDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MantisStatusDTO> findByCriteria(MantisStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<MantisStatus> specification = createSpecification(criteria);
        return mantisStatusMapper.toDto(mantisStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MantisStatusDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MantisStatusDTO> findByCriteria(MantisStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<MantisStatus> specification = createSpecification(criteria);
        final Page<MantisStatus> result = mantisStatusRepository.findAll(specification, page);
        return result.map(mantisStatusMapper::toDto);
    }

    /**
     * Function to convert MantisStatusCriteria to a {@link Specifications}
     */
    private Specifications<MantisStatus> createSpecification(MantisStatusCriteria criteria) {
        Specifications<MantisStatus> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MantisStatus_.id));
            }
            if (criteria.getChangeDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChangeDate(), MantisStatus_.changeDate));
            }
            if (criteria.getMantisId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMantisId(), MantisStatus_.mantis, Mantis_.id));
            }
            if (criteria.getStatusId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStatusId(), MantisStatus_.status, Status_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), MantisStatus_.user, User_.id));
            }
            if (criteria.getApproverId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getApproverId(), MantisStatus_.approver, MantisApprover_.id));
            }
        }
        return specification;
    }

}
