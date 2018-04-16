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
 * Criteria class for the MantisConsumption entity. This class is used in MantisConsumptionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /mantis-consumptions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MantisConsumptionCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter submissionDate;

    private DoubleFilter consumed;

    private DoubleFilter toBill;

    private LongFilter userId;

    private LongFilter mantisId;

    public MantisConsumptionCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateFilter submissionDate) {
        this.submissionDate = submissionDate;
    }

    public DoubleFilter getConsumed() {
        return consumed;
    }

    public void setConsumed(DoubleFilter consumed) {
        this.consumed = consumed;
    }

    public DoubleFilter getToBill() {
        return toBill;
    }

    public void setToBill(DoubleFilter toBill) {
        this.toBill = toBill;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getMantisId() {
        return mantisId;
    }

    public void setMantisId(LongFilter mantisId) {
        this.mantisId = mantisId;
    }

    @Override
    public String toString() {
        return "MantisConsumptionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (submissionDate != null ? "submissionDate=" + submissionDate + ", " : "") +
                (consumed != null ? "consumed=" + consumed + ", " : "") +
                (toBill != null ? "toBill=" + toBill + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (mantisId != null ? "mantisId=" + mantisId + ", " : "") +
            "}";
    }

}
