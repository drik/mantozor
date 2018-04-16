package com.dirks.cool.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MantisConsumption entity.
 */
public class MantisConsumptionDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate submissionDate;

    @NotNull
    private Double consumed;

    @NotNull
    private Double toBill;

    private Long userId;

    private Long mantisId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Double getConsumed() {
        return consumed;
    }

    public void setConsumed(Double consumed) {
        this.consumed = consumed;
    }

    public Double getToBill() {
        return toBill;
    }

    public void setToBill(Double toBill) {
        this.toBill = toBill;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

        MantisConsumptionDTO mantisConsumptionDTO = (MantisConsumptionDTO) o;
        if(mantisConsumptionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mantisConsumptionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MantisConsumptionDTO{" +
            "id=" + getId() +
            ", submissionDate='" + getSubmissionDate() + "'" +
            ", consumed=" + getConsumed() +
            ", toBill=" + getToBill() +
            "}";
    }
}
