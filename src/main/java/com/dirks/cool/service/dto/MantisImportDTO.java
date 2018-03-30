package com.dirks.cool.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the MantisImport entity.
 */
public class MantisImportDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate importDate;

    @Lob
    private byte[] file;
    private String fileContentType;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MantisImportDTO mantisImportDTO = (MantisImportDTO) o;
        if(mantisImportDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mantisImportDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MantisImportDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", importDate='" + getImportDate() + "'" +
            ", file='" + getFile() + "'" +
            "}";
    }
}
