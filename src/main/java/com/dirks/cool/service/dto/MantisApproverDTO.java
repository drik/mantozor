package com.dirks.cool.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MantisApprover entity.
 */
public class MantisApproverDTO implements Serializable {

    private Long id;

    private String fullName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MantisApproverDTO mantisApproverDTO = (MantisApproverDTO) o;
        if(mantisApproverDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mantisApproverDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MantisApproverDTO{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            "}";
    }
}
