package com.dirks.cool.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Mantis.
 */
@Entity
@Table(name = "mantis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mantis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "mantis_number", nullable = false)
    private String mantisNumber;

    @NotNull
    @Column(name = "submission_date", nullable = false)
    private LocalDate submissionDate;
    
    @Column(name = "total_charge")
    private Double totalCharge;
    
    @Transient
    private Double chargeAlreadyConsumed;

    @ManyToOne
    private Project project;

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

    public Mantis mantisNumber(String mantisNumber) {
        this.mantisNumber = mantisNumber;
        return this;
    }

    public void setMantisNumber(String mantisNumber) {
        this.mantisNumber = mantisNumber;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public Mantis submissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
        return this;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Project getProject() {
        return project;
    }

    public Mantis project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Double getTotalCharge() {
		return totalCharge;
	}

	public void setTotalCharge(Double totalCharge) {
		this.totalCharge = totalCharge;
	}

	public Double getChargeAlreadyConsumed() {
		return chargeAlreadyConsumed;
	}

	public void setChargeAlreadyConsumed(Double chargeAlreadyConsumed) {
		this.chargeAlreadyConsumed = chargeAlreadyConsumed;
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
        Mantis mantis = (Mantis) o;
        if (mantis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mantis.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mantis{" +
            "id=" + getId() +
            ", mantisNumber='" + getMantisNumber() + "'" +
            ", submissionDate='" + getSubmissionDate() + "'" +
            "}";
    }
}
