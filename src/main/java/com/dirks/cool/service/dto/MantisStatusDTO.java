package com.dirks.cool.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.dirks.cool.domain.enumeration.Status;

/**
 * A DTO for the MantisStatus entity.
 */
public class MantisStatusDTO implements Serializable {

    private Long id;

    private Status status;

    private LocalDate changeDate;

    private Long mantisId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(LocalDate changeDate) {
        this.changeDate = changeDate;
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

        MantisStatusDTO mantisStatusDTO = (MantisStatusDTO) o;
        if(mantisStatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mantisStatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MantisStatusDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", changeDate='" + getChangeDate() + "'" +
            "}";
    }
}
