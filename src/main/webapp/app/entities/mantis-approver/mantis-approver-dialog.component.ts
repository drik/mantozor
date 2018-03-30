import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MantisApprover } from './mantis-approver.model';
import { MantisApproverPopupService } from './mantis-approver-popup.service';
import { MantisApproverService } from './mantis-approver.service';

@Component({
    selector: 'jhi-mantis-approver-dialog',
    templateUrl: './mantis-approver-dialog.component.html'
})
export class MantisApproverDialogComponent implements OnInit {

    mantisApprover: MantisApprover;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private mantisApproverService: MantisApproverService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mantisApprover.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mantisApproverService.update(this.mantisApprover));
        } else {
            this.subscribeToSaveResponse(
                this.mantisApproverService.create(this.mantisApprover));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MantisApprover>>) {
        result.subscribe((res: HttpResponse<MantisApprover>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MantisApprover) {
        this.eventManager.broadcast({ name: 'mantisApproverListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-mantis-approver-popup',
    template: ''
})
export class MantisApproverPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mantisApproverPopupService: MantisApproverPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mantisApproverPopupService
                    .open(MantisApproverDialogComponent as Component, params['id']);
            } else {
                this.mantisApproverPopupService
                    .open(MantisApproverDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
