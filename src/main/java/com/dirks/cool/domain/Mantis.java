package com.dirks.cool.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.dirks.cool.domain.enumeration.State;

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
    @Column(name = "jhi_number", nullable = false)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Column(name = "submission_date")
    private LocalDate submissionDate;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Referent referent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public Mantis number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public State getState() {
        return state;
    }

    public Mantis state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
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

    public Referent getReferent() {
        return referent;
    }

    public Mantis referent(Referent referent) {
        this.referent = referent;
        return this;
    }

    public void setReferent(Referent referent) {
        this.referent = referent;
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
            ", number='" + getNumber() + "'" +
            ", state='" + getState() + "'" +
            ", submissionDate='" + getSubmissionDate() + "'" +
            "}";
    }
}
