package com.dirks.cool.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the Mantis entity. This class is used in MantisResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /mantis?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MantisCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter mantisNumber;

    private LocalDateFilter submissionDate;

    private LongFilter projectId;

    public MantisCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMantisNumber() {
        return mantisNumber;
    }

    public void setMantisNumber(StringFilter mantisNumber) {
        this.mantisNumber = mantisNumber;
    }

    public LocalDateFilter getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateFilter submissionDate) {
        this.submissionDate = submissionDate;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "MantisCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (mantisNumber != null ? "mantisNumber=" + mantisNumber + ", " : "") +
                (submissionDate != null ? "submissionDate=" + submissionDate + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
            "}";
    }

}
