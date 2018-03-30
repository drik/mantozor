package com.dirks.cool.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MantisStatus entity.
 */
public class MantisStatusDTO implements Serializable {

    private Long id;

    private LocalDate changeDate;

    private Long mantisId;

    private Long statusId;

    private Long userId;

    private Long approverId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long mantisApproverId) {
        this.approverId = mantisApproverId;
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
            ", changeDate='" + getChangeDate() + "'" +
            "}";
    }
}
