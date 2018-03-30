package com.dirks.cool.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MantisImportLine entity.
 */
public class MantisImportLineDTO implements Serializable {

    private Long id;

    @NotNull
    private String mantisNumber;

    private String validationStatus;

    private String project;

    private LocalDate updateDate;

    private String category;

    private String gravity;

    private String augeoReference;

    private String technicalReference;

    private String description;

    private LocalDate submissionDate;

    private LocalDate desiredCommitmentDate;

    private Double estimatedChargeCACF;

    private LocalDate commitmentDateCDS;

    private Double estimatedChargeCDS;

    private LocalDate estimatedDSTDelivreryDate;

    private LocalDate recipeDate;

    private LocalDate productionDate;

    private String devStandardsComplianceScore;

    private String devStandardsComplianceScoreComment;

    private String expressedNeedsComplianceScore;

    private String expressedNeedsComplianceScoreComment;

    private String overallDeadlineRespectScore;

    private String overallDeadlineRespectScoreComment;

    private Long stateId;

    private Long mantisImportId;

    private Long mantisId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMantisNumber() {
        return mantisNumber;
    }

    public void setMantisNumber(String mantisNumber) {
        this.mantisNumber = mantisNumber;
    }

    public String getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGravity() {
        return gravity;
    }

    public void setGravity(String gravity) {
        this.gravity = gravity;
    }

    public String getAugeoReference() {
        return augeoReference;
    }

    public void setAugeoReference(String augeoReference) {
        this.augeoReference = augeoReference;
    }

    public String getTechnicalReference() {
        return technicalReference;
    }

    public void setTechnicalReference(String technicalReference) {
        this.technicalReference = technicalReference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public LocalDate getDesiredCommitmentDate() {
        return desiredCommitmentDate;
    }

    public void setDesiredCommitmentDate(LocalDate desiredCommitmentDate) {
        this.desiredCommitmentDate = desiredCommitmentDate;
    }

    public Double getEstimatedChargeCACF() {
        return estimatedChargeCACF;
    }

    public void setEstimatedChargeCACF(Double estimatedChargeCACF) {
        this.estimatedChargeCACF = estimatedChargeCACF;
    }

    public LocalDate getCommitmentDateCDS() {
        return commitmentDateCDS;
    }

    public void setCommitmentDateCDS(LocalDate commitmentDateCDS) {
        this.commitmentDateCDS = commitmentDateCDS;
    }

    public Double getEstimatedChargeCDS() {
        return estimatedChargeCDS;
    }

    public void setEstimatedChargeCDS(Double estimatedChargeCDS) {
        this.estimatedChargeCDS = estimatedChargeCDS;
    }

    public LocalDate getEstimatedDSTDelivreryDate() {
        return estimatedDSTDelivreryDate;
    }

    public void setEstimatedDSTDelivreryDate(LocalDate estimatedDSTDelivreryDate) {
        this.estimatedDSTDelivreryDate = estimatedDSTDelivreryDate;
    }

    public LocalDate getRecipeDate() {
        return recipeDate;
    }

    public void setRecipeDate(LocalDate recipeDate) {
        this.recipeDate = recipeDate;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public String getDevStandardsComplianceScore() {
        return devStandardsComplianceScore;
    }

    public void setDevStandardsComplianceScore(String devStandardsComplianceScore) {
        this.devStandardsComplianceScore = devStandardsComplianceScore;
    }

    public String getDevStandardsComplianceScoreComment() {
        return devStandardsComplianceScoreComment;
    }

    public void setDevStandardsComplianceScoreComment(String devStandardsComplianceScoreComment) {
        this.devStandardsComplianceScoreComment = devStandardsComplianceScoreComment;
    }

    public String getExpressedNeedsComplianceScore() {
        return expressedNeedsComplianceScore;
    }

    public void setExpressedNeedsComplianceScore(String expressedNeedsComplianceScore) {
        this.expressedNeedsComplianceScore = expressedNeedsComplianceScore;
    }

    public String getExpressedNeedsComplianceScoreComment() {
        return expressedNeedsComplianceScoreComment;
    }

    public void setExpressedNeedsComplianceScoreComment(String expressedNeedsComplianceScoreComment) {
        this.expressedNeedsComplianceScoreComment = expressedNeedsComplianceScoreComment;
    }

    public String getOverallDeadlineRespectScore() {
        return overallDeadlineRespectScore;
    }

    public void setOverallDeadlineRespectScore(String overallDeadlineRespectScore) {
        this.overallDeadlineRespectScore = overallDeadlineRespectScore;
    }

    public String getOverallDeadlineRespectScoreComment() {
        return overallDeadlineRespectScoreComment;
    }

    public void setOverallDeadlineRespectScoreComment(String overallDeadlineRespectScoreComment) {
        this.overallDeadlineRespectScoreComment = overallDeadlineRespectScoreComment;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getMantisImportId() {
        return mantisImportId;
    }

    public void setMantisImportId(Long mantisImportId) {
        this.mantisImportId = mantisImportId;
    }

    public Long getMantisId() {
        return mantisId;
    }

    public void setMantisId(Long mantisId) {
        this.mantisId = mantisId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MantisImportLineDTO mantisImportLineDTO = (MantisImportLineDTO) o;
        if(mantisImportLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mantisImportLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MantisImportLineDTO{" +
            "id=" + getId() +
            ", mantisNumber='" + getMantisNumber() + "'" +
            ", validationStatus='" + getValidationStatus() + "'" +
            ", project='" + getProject() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", category='" + getCategory() + "'" +
            ", gravity='" + getGravity() + "'" +
            ", augeoReference='" + getAugeoReference() + "'" +
            ", technicalReference='" + getTechnicalReference() + "'" +
            ", description='" + getDescription() + "'" +
            ", submissionDate='" + getSubmissionDate() + "'" +
            ", desiredCommitmentDate='" + getDesiredCommitmentDate() + "'" +
            ", estimatedChargeCACF=" + getEstimatedChargeCACF() +
            ", commitmentDateCDS='" + getCommitmentDateCDS() + "'" +
            ", estimatedChargeCDS=" + getEstimatedChargeCDS() +
            ", estimatedDSTDelivreryDate='" + getEstimatedDSTDelivreryDate() + "'" +
            ", recipeDate='" + getRecipeDate() + "'" +
            ", productionDate='" + getProductionDate() + "'" +
            ", devStandardsComplianceScore='" + getDevStandardsComplianceScore() + "'" +
            ", devStandardsComplianceScoreComment='" + getDevStandardsComplianceScoreComment() + "'" +
            ", expressedNeedsComplianceScore='" + getExpressedNeedsComplianceScore() + "'" +
            ", expressedNeedsComplianceScoreComment='" + getExpressedNeedsComplianceScoreComment() + "'" +
            ", overallDeadlineRespectScore='" + getOverallDeadlineRespectScore() + "'" +
            ", overallDeadlineRespectScoreComment='" + getOverallDeadlineRespectScoreComment() + "'" +
            "}";
    }
}
