package com.dirks.cool.service.dto;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the MantisFile entity.
 */
public class MantisFileDTO implements Serializable {

    private Long id;

    @Lob
    private String file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MantisFileDTO mantisFileDTO = (MantisFileDTO) o;
        if(mantisFileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mantisFileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MantisFileDTO{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            "}";
    }
}
