package com.dirks.cool.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MantisConsumption.
 */
@Entity
@Table(name = "mantis_consumption")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MantisConsumption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "submission_date", nullable = false)
    private LocalDate submissionDate;

    @NotNull
    @Column(name = "consumed", nullable = false)
    private Double consumed;

    @NotNull
    @Column(name = "to_bill", nullable = false)
    private Double toBill;

    @ManyToOne
    private User user;

    @ManyToOne
    private Mantis mantis;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public MantisConsumption submissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
        return this;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Double getConsumed() {
        return consumed;
    }

    public MantisConsumption consumed(Double consumed) {
        this.consumed = consumed;
        return this;
    }

    public void setConsumed(Double consumed) {
        this.consumed = consumed;
    }

    public Double getToBill() {
        return toBill;
    }

    public MantisConsumption toBill(Double toBill) {
        this.toBill = toBill;
        return this;
    }

    public void setToBill(Double toBill) {
        this.toBill = toBill;
    }

    public User getUser() {
        return user;
    }

    public MantisConsumption user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Mantis getMantis() {
        return mantis;
    }

    public MantisConsumption mantis(Mantis mantis) {
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
        MantisConsumption mantisConsumption = (MantisConsumption) o;
        if (mantisConsumption.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mantisConsumption.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MantisConsumption{" +
            "id=" + getId() +
            ", submissionDate='" + getSubmissionDate() + "'" +
            ", consumed=" + getConsumed() +
            ", toBill=" + getToBill() +
            "}";
    }
}
