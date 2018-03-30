package com.dirks.cool.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the MantisImportLine entity. This class is used in MantisImportLineResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /mantis-import-lines?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MantisImportLineCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter mantisNumber;

    private StringFilter validationStatus;

    private StringFilter project;

    private LocalDateFilter updateDate;

    private StringFilter category;

    private StringFilter gravity;

    private StringFilter augeoReference;

    private StringFilter technicalReference;

    private StringFilter description;

    private LocalDateFilter submissionDate;

    private LocalDateFilter desiredCommitmentDate;

    private DoubleFilter estimatedChargeCACF;

    private LocalDateFilter commitmentDateCDS;

    private DoubleFilter estimatedChargeCDS;

    private LocalDateFilter estimatedDSTDelivreryDate;

    private LocalDateFilter recipeDate;

    private LocalDateFilter productionDate;

    private StringFilter devStandardsComplianceScore;

    private StringFilter devStandardsComplianceScoreComment;

    private StringFilter expressedNeedsComplianceScore;

    private StringFilter expressedNeedsComplianceScoreComment;

    private StringFilter overallDeadlineRespectScore;

    private StringFilter overallDeadlineRespectScoreComment;

    private LongFilter stateId;

    private LongFilter mantisImportId;

    private LongFilter mantisId;

    public MantisImportLineCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMantisNumber() {
        return mantisNumber;
    }

    public void setMantisNumber(StringFilter mantisNumber) {
        this.mantisNumber = mantisNumber;
    }

    public StringFilter getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(StringFilter validationStatus) {
        this.validationStatus = validationStatus;
    }

    public StringFilter getProject() {
        return project;
    }

    public void setProject(StringFilter project) {
        this.project = project;
    }

    public LocalDateFilter getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateFilter updateDate) {
        this.updateDate = updateDate;
    }

    public StringFilter getCategory() {
        return category;
    }

    public void setCategory(StringFilter category) {
        this.category = category;
    }

    public StringFilter getGravity() {
        return gravity;
    }

    public void setGravity(StringFilter gravity) {
        this.gravity = gravity;
    }

    public StringFilter getAugeoReference() {
        return augeoReference;
    }

    public void setAugeoReference(StringFilter augeoReference) {
        this.augeoReference = augeoReference;
    }

    public StringFilter getTechnicalReference() {
        return technicalReference;
    }

    public void setTechnicalReference(StringFilter technicalReference) {
        this.technicalReference = technicalReference;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LocalDateFilter getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateFilter submissionDate) {
        this.submissionDate = submissionDate;
    }

    public LocalDateFilter getDesiredCommitmentDate() {
        return desiredCommitmentDate;
    }

    public void setDesiredCommitmentDate(LocalDateFilter desiredCommitmentDate) {
        this.desiredCommitmentDate = desiredCommitmentDate;
    }

    public DoubleFilter getEstimatedChargeCACF() {
        return estimatedChargeCACF;
    }

    public void setEstimatedChargeCACF(DoubleFilter estimatedChargeCACF) {
        this.estimatedChargeCACF = estimatedChargeCACF;
    }

    public LocalDateFilter getCommitmentDateCDS() {
        return commitmentDateCDS;
    }

    public void setCommitmentDateCDS(LocalDateFilter commitmentDateCDS) {
        this.commitmentDateCDS = commitmentDateCDS;
    }

    public DoubleFilter getEstimatedChargeCDS() {
        return estimatedChargeCDS;
    }

    public void setEstimatedChargeCDS(DoubleFilter estimatedChargeCDS) {
        this.estimatedChargeCDS = estimatedChargeCDS;
    }

    public LocalDateFilter getEstimatedDSTDelivreryDate() {
        return estimatedDSTDelivreryDate;
    }

    public void setEstimatedDSTDelivreryDate(LocalDateFilter estimatedDSTDelivreryDate) {
        this.estimatedDSTDelivreryDate = estimatedDSTDelivreryDate;
    }

    public LocalDateFilter getRecipeDate() {
        return recipeDate;
    }

    public void setRecipeDate(LocalDateFilter recipeDate) {
        this.recipeDate = recipeDate;
    }

    public LocalDateFilter getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDateFilter productionDate) {
        this.productionDate = productionDate;
    }

    public StringFilter getDevStandardsComplianceScore() {
        return devStandardsComplianceScore;
    }

    public void setDevStandardsComplianceScore(StringFilter devStandardsComplianceScore) {
        this.devStandardsComplianceScore = devStandardsComplianceScore;
    }

    public StringFilter getDevStandardsComplianceScoreComment() {
        return devStandardsComplianceScoreComment;
    }

    public void setDevStandardsComplianceScoreComment(StringFilter devStandardsComplianceScoreComment) {
        this.devStandardsComplianceScoreComment = devStandardsComplianceScoreComment;
    }

    public StringFilter getExpressedNeedsComplianceScore() {
        return expressedNeedsComplianceScore;
    }

    public void setExpressedNeedsComplianceScore(StringFilter expressedNeedsComplianceScore) {
        this.expressedNeedsComplianceScore = expressedNeedsComplianceScore;
    }

    public StringFilter getExpressedNeedsComplianceScoreComment() {
        return expressedNeedsComplianceScoreComment;
    }

    public void setExpressedNeedsComplianceScoreComment(StringFilter expressedNeedsComplianceScoreComment) {
        this.expressedNeedsComplianceScoreComment = expressedNeedsComplianceScoreComment;
    }

    public StringFilter getOverallDeadlineRespectScore() {
        return overallDeadlineRespectScore;
    }

    public void setOverallDeadlineRespectScore(StringFilter overallDeadlineRespectScore) {
        this.overallDeadlineRespectScore = overallDeadlineRespectScore;
    }

    public StringFilter getOverallDeadlineRespectScoreComment() {
        return overallDeadlineRespectScoreComment;
    }

    public void setOverallDeadlineRespectScoreComment(StringFilter overallDeadlineRespectScoreComment) {
        this.overallDeadlineRespectScoreComment = overallDeadlineRespectScoreComment;
    }

    public LongFilter getStateId() {
        return stateId;
    }

    public void setStateId(LongFilter stateId) {
        this.stateId = stateId;
    }

    public LongFilter getMantisImportId() {
        return mantisImportId;
    }

    public void setMantisImportId(LongFilter mantisImportId) {
        this.mantisImportId = mantisImportId;
    }

    public LongFilter getMantisId() {
        return mantisId;
    }

    public void setMantisId(LongFilter mantisId) {
        this.mantisId = mantisId;
    }

    @Override
    public String toString() {
        return "MantisImportLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (mantisNumber != null ? "mantisNumber=" + mantisNumber + ", " : "") +
                (validationStatus != null ? "validationStatus=" + validationStatus + ", " : "") +
                (project != null ? "project=" + project + ", " : "") +
                (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
                (category != null ? "category=" + category + ", " : "") +
                (gravity != null ? "gravity=" + gravity + ", " : "") +
                (augeoReference != null ? "augeoReference=" + augeoReference + ", " : "") +
                (technicalReference != null ? "technicalReference=" + technicalReference + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (submissionDate != null ? "submissionDate=" + submissionDate + ", " : "") +
                (desiredCommitmentDate != null ? "desiredCommitmentDate=" + desiredCommitmentDate + ", " : "") +
                (estimatedChargeCACF != null ? "estimatedChargeCACF=" + estimatedChargeCACF + ", " : "") +
                (commitmentDateCDS != null ? "commitmentDateCDS=" + commitmentDateCDS + ", " : "") +
                (estimatedChargeCDS != null ? "estimatedChargeCDS=" + estimatedChargeCDS + ", " : "") +
                (estimatedDSTDelivreryDate != null ? "estimatedDSTDelivreryDate=" + estimatedDSTDelivreryDate + ", " : "") +
                (recipeDate != null ? "recipeDate=" + recipeDate + ", " : "") +
                (productionDate != null ? "productionDate=" + productionDate + ", " : "") +
                (devStandardsComplianceScore != null ? "devStandardsComplianceScore=" + devStandardsComplianceScore + ", " : "") +
                (devStandardsComplianceScoreComment != null ? "devStandardsComplianceScoreComment=" + devStandardsComplianceScoreComment + ", " : "") +
                (expressedNeedsComplianceScore != null ? "expressedNeedsComplianceScore=" + expressedNeedsComplianceScore + ", " : "") +
                (expressedNeedsComplianceScoreComment != null ? "expressedNeedsComplianceScoreComment=" + expressedNeedsComplianceScoreComment + ", " : "") +
                (overallDeadlineRespectScore != null ? "overallDeadlineRespectScore=" + overallDeadlineRespectScore + ", " : "") +
                (overallDeadlineRespectScoreComment != null ? "overallDeadlineRespectScoreComment=" + overallDeadlineRespectScoreComment + ", " : "") +
                (stateId != null ? "stateId=" + stateId + ", " : "") +
                (mantisImportId != null ? "mantisImportId=" + mantisImportId + ", " : "") +
                (mantisId != null ? "mantisId=" + mantisId + ", " : "") +
            "}";
    }

}
