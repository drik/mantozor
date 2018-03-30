package com.dirks.cool.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MantisImportLine.
 */
@Entity
@Table(name = "mantis_import_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MantisImportLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "mantis_number", nullable = false)
    private String mantisNumber;

    @Column(name = "validation_status")
    private String validationStatus;

    @Column(name = "project")
    private String project;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "category")
    private String category;

    @Column(name = "gravity")
    private String gravity;

    @Column(name = "augeo_reference")
    private String augeoReference;

    @Column(name = "technical_reference")
    private String technicalReference;

    @Column(name = "description")
    private String description;

    @Column(name = "submission_date")
    private LocalDate submissionDate;

    @Column(name = "desired_commitment_date")
    private LocalDate desiredCommitmentDate;

    @Column(name = "estimated_charge_cacf")
    private Double estimatedChargeCACF;

    @Column(name = "commitment_date_cds")
    private LocalDate commitmentDateCDS;

    @Column(name = "estimated_charge_cds")
    private Double estimatedChargeCDS;

    @Column(name = "estimated_dst_delivrery_date")
    private LocalDate estimatedDSTDelivreryDate;

    @Column(name = "recipe_date")
    private LocalDate recipeDate;

    @Column(name = "production_date")
    private LocalDate productionDate;

    @Column(name = "dev_standards_compliance_score")
    private String devStandardsComplianceScore;

    @Column(name = "dev_standards_compliance_score_comment")
    private String devStandardsComplianceScoreComment;

    @Column(name = "expressed_needs_compliance_score")
    private String expressedNeedsComplianceScore;

    @Column(name = "expressed_needs_compliance_score_comment")
    private String expressedNeedsComplianceScoreComment;

    @Column(name = "overall_deadline_respect_score")
    private String overallDeadlineRespectScore;

    @Column(name = "overall_deadline_respect_score_comment")
    private String overallDeadlineRespectScoreComment;

    @ManyToOne
    private State state;

    @ManyToOne
    private MantisImport mantisImport;

    @ManyToOne
    private Mantis mantis;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMantisNumber() {
        return mantisNumber;
    }

    public MantisImportLine mantisNumber(String mantisNumber) {
        this.mantisNumber = mantisNumber;
        return this;
    }

    public void setMantisNumber(String mantisNumber) {
        this.mantisNumber = mantisNumber;
    }

    public String getValidationStatus() {
        return validationStatus;
    }

    public MantisImportLine validationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
        return this;
    }

    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }

    public String getProject() {
        return project;
    }

    public MantisImportLine project(String project) {
        this.project = project;
        return this;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public MantisImportLine updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getCategory() {
        return category;
    }

    public MantisImportLine category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGravity() {
        return gravity;
    }

    public MantisImportLine gravity(String gravity) {
        this.gravity = gravity;
        return this;
    }

    public void setGravity(String gravity) {
        this.gravity = gravity;
    }

    public String getAugeoReference() {
        return augeoReference;
    }

    public MantisImportLine augeoReference(String augeoReference) {
        this.augeoReference = augeoReference;
        return this;
    }

    public void setAugeoReference(String augeoReference) {
        this.augeoReference = augeoReference;
    }

    public String getTechnicalReference() {
        return technicalReference;
    }

    public MantisImportLine technicalReference(String technicalReference) {
        this.technicalReference = technicalReference;
        return this;
    }

    public void setTechnicalReference(String technicalReference) {
        this.technicalReference = technicalReference;
    }

    public String getDescription() {
        return description;
    }

    public MantisImportLine description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public MantisImportLine submissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
        return this;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public LocalDate getDesiredCommitmentDate() {
        return desiredCommitmentDate;
    }

    public MantisImportLine desiredCommitmentDate(LocalDate desiredCommitmentDate) {
        this.desiredCommitmentDate = desiredCommitmentDate;
        return this;
    }

    public void setDesiredCommitmentDate(LocalDate desiredCommitmentDate) {
        this.desiredCommitmentDate = desiredCommitmentDate;
    }

    public Double getEstimatedChargeCACF() {
        return estimatedChargeCACF;
    }

    public MantisImportLine estimatedChargeCACF(Double estimatedChargeCACF) {
        this.estimatedChargeCACF = estimatedChargeCACF;
        return this;
    }

    public void setEstimatedChargeCACF(Double estimatedChargeCACF) {
        this.estimatedChargeCACF = estimatedChargeCACF;
    }

    public LocalDate getCommitmentDateCDS() {
        return commitmentDateCDS;
    }

    public MantisImportLine commitmentDateCDS(LocalDate commitmentDateCDS) {
        this.commitmentDateCDS = commitmentDateCDS;
        return this;
    }

    public void setCommitmentDateCDS(LocalDate commitmentDateCDS) {
        this.commitmentDateCDS = commitmentDateCDS;
    }

    public Double getEstimatedChargeCDS() {
        return estimatedChargeCDS;
    }

    public MantisImportLine estimatedChargeCDS(Double estimatedChargeCDS) {
        this.estimatedChargeCDS = estimatedChargeCDS;
        return this;
    }

    public void setEstimatedChargeCDS(Double estimatedChargeCDS) {
        this.estimatedChargeCDS = estimatedChargeCDS;
    }

    public LocalDate getEstimatedDSTDelivreryDate() {
        return estimatedDSTDelivreryDate;
    }

    public MantisImportLine estimatedDSTDelivreryDate(LocalDate estimatedDSTDelivreryDate) {
        this.estimatedDSTDelivreryDate = estimatedDSTDelivreryDate;
        return this;
    }

    public void setEstimatedDSTDelivreryDate(LocalDate estimatedDSTDelivreryDate) {
        this.estimatedDSTDelivreryDate = estimatedDSTDelivreryDate;
    }

    public LocalDate getRecipeDate() {
        return recipeDate;
    }

    public MantisImportLine recipeDate(LocalDate recipeDate) {
        this.recipeDate = recipeDate;
        return this;
    }

    public void setRecipeDate(LocalDate recipeDate) {
        this.recipeDate = recipeDate;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public MantisImportLine productionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
        return this;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public String getDevStandardsComplianceScore() {
        return devStandardsComplianceScore;
    }

    public MantisImportLine devStandardsComplianceScore(String devStandardsComplianceScore) {
        this.devStandardsComplianceScore = devStandardsComplianceScore;
        return this;
    }

    public void setDevStandardsComplianceScore(String devStandardsComplianceScore) {
        this.devStandardsComplianceScore = devStandardsComplianceScore;
    }

    public String getDevStandardsComplianceScoreComment() {
        return devStandardsComplianceScoreComment;
    }

    public MantisImportLine devStandardsComplianceScoreComment(String devStandardsComplianceScoreComment) {
        this.devStandardsComplianceScoreComment = devStandardsComplianceScoreComment;
        return this;
    }

    public void setDevStandardsComplianceScoreComment(String devStandardsComplianceScoreComment) {
        this.devStandardsComplianceScoreComment = devStandardsComplianceScoreComment;
    }

    public String getExpressedNeedsComplianceScore() {
        return expressedNeedsComplianceScore;
    }

    public MantisImportLine expressedNeedsComplianceScore(String expressedNeedsComplianceScore) {
        this.expressedNeedsComplianceScore = expressedNeedsComplianceScore;
        return this;
    }

    public void setExpressedNeedsComplianceScore(String expressedNeedsComplianceScore) {
        this.expressedNeedsComplianceScore = expressedNeedsComplianceScore;
    }

    public String getExpressedNeedsComplianceScoreComment() {
        return expressedNeedsComplianceScoreComment;
    }

    public MantisImportLine expressedNeedsComplianceScoreComment(String expressedNeedsComplianceScoreComment) {
        this.expressedNeedsComplianceScoreComment = expressedNeedsComplianceScoreComment;
        return this;
    }

    public void setExpressedNeedsComplianceScoreComment(String expressedNeedsComplianceScoreComment) {
        this.expressedNeedsComplianceScoreComment = expressedNeedsComplianceScoreComment;
    }

    public String getOverallDeadlineRespectScore() {
        return overallDeadlineRespectScore;
    }

    public MantisImportLine overallDeadlineRespectScore(String overallDeadlineRespectScore) {
        this.overallDeadlineRespectScore = overallDeadlineRespectScore;
        return this;
    }

    public void setOverallDeadlineRespectScore(String overallDeadlineRespectScore) {
        this.overallDeadlineRespectScore = overallDeadlineRespectScore;
    }

    public String getOverallDeadlineRespectScoreComment() {
        return overallDeadlineRespectScoreComment;
    }

    public MantisImportLine overallDeadlineRespectScoreComment(String overallDeadlineRespectScoreComment) {
        this.overallDeadlineRespectScoreComment = overallDeadlineRespectScoreComment;
        return this;
    }

    public void setOverallDeadlineRespectScoreComment(String overallDeadlineRespectScoreComment) {
        this.overallDeadlineRespectScoreComment = overallDeadlineRespectScoreComment;
    }

    public State getState() {
        return state;
    }

    public MantisImportLine state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public MantisImport getMantisImport() {
        return mantisImport;
    }

    public MantisImportLine mantisImport(MantisImport mantisImport) {
        this.mantisImport = mantisImport;
        return this;
    }

    public void setMantisImport(MantisImport mantisImport) {
        this.mantisImport = mantisImport;
    }

    public Mantis getMantis() {
        return mantis;
    }

    public MantisImportLine mantis(Mantis mantis) {
        this.mantis = mantis;
        return this;
    }

    public void setMantis(Mantis mantis) {
        this.mantis = mantis;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MantisImportLine mantisImportLine = (MantisImportLine) o;
        if (mantisImportLine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mantisImportLine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MantisImportLine{" +
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
