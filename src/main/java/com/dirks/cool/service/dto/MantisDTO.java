package com.dirks.cool.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.dirks.cool.domain.enumeration.State;

/**
 * A DTO for the Mantis entity.
 */
public class MantisDTO implements Serializable {

    private Long id;

    @NotNull
    private String number;

    private State state;

    private LocalDate submissionDate;

    private Long projectId;

    private Long referentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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

    public Long getReferentId() {
        return referentId;
    }

    public void setReferentId(Long referentId) {
        this.referentId = referentId;
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
            ", number='" + getNumber() + "'" +
            ", state='" + getState() + "'" +
            ", submissionDate='" + getSubmissionDate() + "'" +
            "}";
    }
}
