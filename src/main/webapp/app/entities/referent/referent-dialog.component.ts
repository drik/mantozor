import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Referent } from './referent.model';
import { ReferentPopupService } from './referent-popup.service';
import { ReferentService } from './referent.service';

@Component({
    selector: 'jhi-referent-dialog',
    templateUrl: './referent-dialog.component.html'
})
export class ReferentDialogComponent implements OnInit {

    referent: Referent;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private referentService: ReferentService,
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
        if (this.referent.id !== undefined) {
            this.subscribeToSaveResponse(
                this.referentService.update(this.referent));
        } else {
            this.subscribeToSaveResponse(
                this.referentService.create(this.referent));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Referent>>) {
        result.subscribe((res: HttpResponse<Referent>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Referent) {
        this.eventManager.broadcast({ name: 'referentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-referent-popup',
    template: ''
})
export class ReferentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private referentPopupService: ReferentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.referentPopupService
                    .open(ReferentDialogComponent as Component, params['id']);
            } else {
                this.referentPopupService
                    .open(ReferentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
