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
 * Criteria class for the MantisStatus entity. This class is used in MantisStatusResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /mantis-statuses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MantisStatusCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LocalDateFilter changeDate;

    private LongFilter mantisId;

    private LongFilter statusId;

    private LongFilter userId;

    private LongFilter approverId;

    public MantisStatusCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(LocalDateFilter changeDate) {
        this.changeDate = changeDate;
    }

    public LongFilter getMantisId() {
        return mantisId;
    }

    public void setMantisId(LongFilter mantisId) {
        this.mantisId = mantisId;
    }

    public LongFilter getStatusId() {
        return statusId;
    }

    public void setStatusId(LongFilter statusId) {
        this.statusId = statusId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getApproverId() {
        return approverId;
    }

    public void setApproverId(LongFilter approverId) {
        this.approverId = approverId;
    }

    @Override
    public String toString() {
        return "MantisStatusCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (changeDate != null ? "changeDate=" + changeDate + ", " : "") +
                (mantisId != null ? "mantisId=" + mantisId + ", " : "") +
                (statusId != null ? "statusId=" + statusId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (approverId != null ? "approverId=" + approverId + ", " : "") +
            "}";
    }

}
