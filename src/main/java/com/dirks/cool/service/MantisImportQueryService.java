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

import com.dirks.cool.domain.MantisImport;
import com.dirks.cool.domain.*; // for static metamodels
import com.dirks.cool.repository.MantisImportRepository;
import com.dirks.cool.service.dto.MantisImportCriteria;

import com.dirks.cool.service.dto.MantisImportDTO;
import com.dirks.cool.service.mapper.MantisImportMapper;

/**
 * Service for executing complex queries for MantisImport entities in the database.
 * The main input is a {@link MantisImportCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MantisImportDTO} or a {@link Page} of {@link MantisImportDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MantisImportQueryService extends QueryService<MantisImport> {

    private final Logger log = LoggerFactory.getLogger(MantisImportQueryService.class);


    private final MantisImportRepository mantisImportRepository;

    private final MantisImportMapper mantisImportMapper;

    public MantisImportQueryService(MantisImportRepository mantisImportRepository, MantisImportMapper mantisImportMapper) {
        this.mantisImportRepository = mantisImportRepository;
        this.mantisImportMapper = mantisImportMapper;
    }

    /**
     * Return a {@link List} of {@link MantisImportDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MantisImportDTO> findByCriteria(MantisImportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<MantisImport> specification = createSpecification(criteria);
        return mantisImportMapper.toDto(mantisImportRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MantisImportDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MantisImportDTO> findByCriteria(MantisImportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<MantisImport> specification = createSpecification(criteria);
        final Page<MantisImport> result = mantisImportRepository.findAll(specification, page);
        return result.map(mantisImportMapper::toDto);
    }

    /**
     * Function to convert MantisImportCriteria to a {@link Specifications}
     */
    private Specifications<MantisImport> createSpecification(MantisImportCriteria criteria) {
        Specifications<MantisImport> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MantisImport_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MantisImport_.name));
            }
            if (criteria.getImportDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getImportDate(), MantisImport_.importDate));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), MantisImport_.user, User_.id));
            }
        }
        return specification;
    }

}
