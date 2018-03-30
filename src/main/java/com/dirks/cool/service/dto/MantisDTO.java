package com.dirks.cool.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Mantis entity.
 */
public class MantisDTO implements Serializable {

    private Long id;

    @NotNull
    private String mantisNumber;

    private LocalDate submissionDate;

    private Long projectId;

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

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MantisDTO mantisDTO = (MantisDTO) o;
        if(mantisDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mantisDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MantisDTO{" +
            "id=" + getId() +
            ", mantisNumber='" + getMantisNumber() + "'" +
            ", submissionDate='" + getSubmissionDate() + "'" +
            "}";
    }
}
