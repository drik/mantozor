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

import com.dirks.cool.domain.MantisImportLine;
import com.dirks.cool.domain.*; // for static metamodels
import com.dirks.cool.repository.MantisImportLineRepository;
import com.dirks.cool.service.dto.MantisImportLineCriteria;

import com.dirks.cool.service.dto.MantisImportLineDTO;
import com.dirks.cool.service.dto.MantisStatusDTO;
import com.dirks.cool.service.mapper.MantisImportLineMapper;

/**
 * Service for executing complex queries for MantisImportLine entities in the database.
 * The main input is a {@link MantisImportLineCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MantisImportLineDTO} or a {@link Page} of {@link MantisImportLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MantisImportLineQueryService extends QueryService<MantisImportLine> {

    private final Logger log = LoggerFactory.getLogger(MantisImportLineQueryService.class);


    private final MantisImportLineRepository mantisImportLineRepository;
    
    private final MantisStatusService mantisStatusService;

    private final MantisImportLineMapper mantisImportLineMapper;

    public MantisImportLineQueryService(MantisImportLineRepository mantisImportLineRepository, MantisImportLineMapper mantisImportLineMapper, MantisStatusService mantisStatusService) {
        this.mantisImportLineRepository = mantisImportLineRepository;
        this.mantisImportLineMapper = mantisImportLineMapper;
        this.mantisStatusService = mantisStatusService;
    }

    /**
     * Return a {@link List} of {@link MantisImportLineDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MantisImportLineDTO> findByCriteria(MantisImportLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<MantisImportLine> specification = createSpecification(criteria);
        return mantisImportLineMapper.toDto(mantisImportLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MantisImportLineDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MantisImportLineDTO> findByCriteria(MantisImportLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<MantisImportLine> specification = createSpecification(criteria);
        final Page<MantisImportLine> result = mantisImportLineRepository.findAll(specification, page);
        Page<MantisImportLineDTO> pages = result.map(mantisImportLineMapper::toDto);
        for(int i = 0; i < pages.getNumberOfElements(); i++) {
        	MantisStatusDTO mantisStatus = mantisStatusService.findLastOneForMantis(pages.getContent().get(i).getMantisId());
        	pages.getContent().get(i).setMantisStatus(mantisStatus);
        }
        return pages;
    }

    /**
     * Function to convert MantisImportLineCriteria to a {@link Specifications}
     */
    private Specifications<MantisImportLine> createSpecification(MantisImportLineCriteria criteria) {
        Specifications<MantisImportLine> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MantisImportLine_.id));
            }
            if (criteria.getMantisNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMantisNumber(), MantisImportLine_.mantisNumber));
            }
            if (criteria.getValidationStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValidationStatus(), MantisImportLine_.validationStatus));
            }
            if (criteria.getProject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProject(), MantisImportLine_.project));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), MantisImportLine_.updateDate));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategory(), MantisImportLine_.category));
            }
            if (criteria.getGravity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGravity(), MantisImportLine_.gravity));
            }
            if (criteria.getAugeoReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAugeoReference(), MantisImportLine_.augeoReference));
            }
            if (criteria.getTechnicalReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTechnicalReference(), MantisImportLine_.technicalReference));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), MantisImportLine_.description));
            }
            if (criteria.getSubmissionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubmissionDate(), MantisImportLine_.submissionDate));
            }
            if (criteria.getDesiredCommitmentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDesiredCommitmentDate(), MantisImportLine_.desiredCommitmentDate));
            }
            if (criteria.getEstimatedChargeCACF() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimatedChargeCACF(), MantisImportLine_.estimatedChargeCACF));
            }
            if (criteria.getCommitmentDateCDS() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommitmentDateCDS(), MantisImportLine_.commitmentDateCDS));
            }
            if (criteria.getEstimatedChargeCDS() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimatedChargeCDS(), MantisImportLine_.estimatedChargeCDS));
            }
            if (criteria.getEstimatedDSTDelivreryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimatedDSTDelivreryDate(), MantisImportLine_.estimatedDSTDelivreryDate));
            }
            if (criteria.getRecipeDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRecipeDate(), MantisImportLine_.recipeDate));
            }
            if (criteria.getProductionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductionDate(), MantisImportLine_.productionDate));
            }
            if (criteria.getDevStandardsComplianceScore() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDevStandardsComplianceScore(), MantisImportLine_.devStandardsComplianceScore));
            }
            if (criteria.getDevStandardsComplianceScoreComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDevStandardsComplianceScoreComment(), MantisImportLine_.devStandardsComplianceScoreComment));
            }
            if (criteria.getExpressedNeedsComplianceScore() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpressedNeedsComplianceScore(), MantisImportLine_.expressedNeedsComplianceScore));
            }
            if (criteria.getExpressedNeedsComplianceScoreComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpressedNeedsComplianceScoreComment(), MantisImportLine_.expressedNeedsComplianceScoreComment));
            }
            if (criteria.getOverallDeadlineRespectScore() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOverallDeadlineRespectScore(), MantisImportLine_.overallDeadlineRespectScore));
            }
            if (criteria.getOverallDeadlineRespectScoreComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOverallDeadlineRespectScoreComment(), MantisImportLine_.overallDeadlineRespectScoreComment));
            }
            if (criteria.getStateId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStateId(), MantisImportLine_.state, State_.id));
            }
            if (criteria.getMantisImportId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMantisImportId(), MantisImportLine_.mantisImport, MantisImport_.id));
            }
            if (criteria.getMantisId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMantisId(), MantisImportLine_.mantis, Mantis_.id));
            }
        }
        return specification;
    }

}
