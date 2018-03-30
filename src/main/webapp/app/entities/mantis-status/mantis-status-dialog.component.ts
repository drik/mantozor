import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MantisStatus } from './mantis-status.model';
import { MantisStatusPopupService } from './mantis-status-popup.service';
import { MantisStatusService } from './mantis-status.service';
import { Mantis, MantisService } from '../mantis';
import { Status, StatusService } from '../status';
import { User, UserService } from '../../shared';
import { MantisApprover, MantisApproverService } from '../mantis-approver';

@Component({
    selector: 'jhi-mantis-status-dialog',
    templateUrl: './mantis-status-dialog.component.html'
})
export class MantisStatusDialogComponent implements OnInit {

    mantisStatus: MantisStatus;
    isSaving: boolean;

    mantis: Mantis[];

    statuses: Status[];

    users: User[];

    mantisapprovers: MantisApprover[];
    changeDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mantisStatusService: MantisStatusService,
        private mantisService: MantisService,
        private statusService: StatusService,
        private userService: UserService,
        private mantisApproverService: MantisApproverService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.mantisService.query()
            .subscribe((res: HttpResponse<Mantis[]>) => { this.mantis = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.statusService.query()
            .subscribe((res: HttpResponse<Status[]>) => { this.statuses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.mantisApproverService.query()
            .subscribe((res: HttpResponse<MantisApprover[]>) => { this.mantisapprovers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mantisStatus.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mantisStatusService.update(this.mantisStatus));
        } else {
            this.subscribeToSaveResponse(
                this.mantisStatusService.create(this.mantisStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MantisStatus>>) {
        result.subscribe((res: HttpResponse<MantisStatus>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MantisStatus) {
        this.eventManager.broadcast({ name: 'mantisStatusListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMantisById(index: number, item: Mantis) {
        return item.id;
    }

    trackStatusById(index: number, item: Status) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackMantisApproverById(index: number, item: MantisApprover) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-mantis-status-popup',
    template: ''
})
export class MantisStatusPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mantisStatusPopupService: MantisStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mantisStatusPopupService
                    .open(MantisStatusDialogComponent as Component, params['id']);
            } else {
                this.mantisStatusPopupService
                    .open(MantisStatusDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
