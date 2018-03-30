import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MantisImportLine } from './mantis-import-line.model';
import { MantisImportLinePopupService } from './mantis-import-line-popup.service';
import { MantisImportLineService } from './mantis-import-line.service';
import { State, StateService } from '../state';
import { MantisImport, MantisImportService } from '../mantis-import';
import { Mantis, MantisService } from '../mantis';

@Component({
    selector: 'jhi-mantis-import-line-dialog',
    templateUrl: './mantis-import-line-dialog.component.html'
})
export class MantisImportLineDialogComponent implements OnInit {

    mantisImportLine: MantisImportLine;
    isSaving: boolean;

    states: State[];

    mantisimports: MantisImport[];

    mantis: Mantis[];
    updateDateDp: any;
    submissionDateDp: any;
    desiredCommitmentDateDp: any;
    commitmentDateCDSDp: any;
    estimatedDSTDelivreryDateDp: any;
    recipeDateDp: any;
    productionDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mantisImportLineService: MantisImportLineService,
        private stateService: StateService,
        private mantisImportService: MantisImportService,
        private mantisService: MantisService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.stateService.query()
            .subscribe((res: HttpResponse<State[]>) => { this.states = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.mantisImportService.query()
            .subscribe((res: HttpResponse<MantisImport[]>) => { this.mantisimports = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.mantisService.query()
            .subscribe((res: HttpResponse<Mantis[]>) => { this.mantis = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mantisImportLine.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mantisImportLineService.update(this.mantisImportLine));
        } else {
            this.subscribeToSaveResponse(
                this.mantisImportLineService.create(this.mantisImportLine));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MantisImportLine>>) {
        result.subscribe((res: HttpResponse<MantisImportLine>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MantisImportLine) {
        this.eventManager.broadcast({ name: 'mantisImportLineListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackStateById(index: number, item: State) {
        return item.id;
    }

    trackMantisImportById(index: number, item: MantisImport) {
        return item.id;
    }

    trackMantisById(index: number, item: Mantis) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-mantis-import-line-popup',
    template: ''
})
export class MantisImportLinePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mantisImportLinePopupService: MantisImportLinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mantisImportLinePopupService
                    .open(MantisImportLineDialogComponent as Component, params['id']);
            } else {
                this.mantisImportLinePopupService
                    .open(MantisImportLineDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
