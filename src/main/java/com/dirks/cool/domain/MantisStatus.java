package com.dirks.cool.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.dirks.cool.domain.enumeration.Status;

/**
 * A MantisStatus.
 */
@Entity
@Table(name = "mantis_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MantisStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "change_date")
    private LocalDate changeDate;

    @ManyToOne
    private Mantis mantis;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public MantisStatus status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getChangeDate() {
        return changeDate;
    }

    public MantisStatus changeDate(LocalDate changeDate) {
        this.changeDate = changeDate;
        return this;
    }

    public void setChangeDate(LocalDate changeDate) {
        this.changeDate = changeDate;
    }

    public Mantis getMantis() {
        return mantis;
    }

    public MantisStatus mantis(Mantis mantis) {
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
        MantisStatus mantisStatus = (MantisStatus) o;
        if (mantisStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mantisStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MantisStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", changeDate='" + getChangeDate() + "'" +
            "}";
    }
}
