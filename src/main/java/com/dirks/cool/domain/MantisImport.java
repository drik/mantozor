package com.dirks.cool.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MantisImport.
 */
@Entity
@Table(name = "mantis_import")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MantisImport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "import_date")
    private LocalDate importDate;

    @Lob
    @Column(name = "jhi_file")
    private byte[] file;

    @Column(name = "jhi_file_content_type")
    private String fileContentType;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MantisImport name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public MantisImport importDate(LocalDate importDate) {
        this.importDate = importDate;
        return this;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    public byte[] getFile() {
        return file;
    }

    public MantisImport file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public MantisImport fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public User getUser() {
        return user;
    }

    public MantisImport user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        MantisImport mantisImport = (MantisImport) o;
        if (mantisImport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mantisImport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MantisImport{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", importDate='" + getImportDate() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            "}";
    }
}
