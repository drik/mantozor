import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MantisConsumption } from './mantis-consumption.model';
import { MantisConsumptionPopupService } from './mantis-consumption-popup.service';
import { MantisConsumptionService } from './mantis-consumption.service';
import { User, UserService } from '../../shared';
import { Mantis, MantisService } from '../mantis';

@Component({
    selector: 'jhi-mantis-consumption-dialog',
    templateUrl: './mantis-consumption-dialog.component.html'
})
export class MantisConsumptionDialogComponent implements OnInit {

    mantisConsumption: MantisConsumption;
    isSaving: boolean;

    users: User[];

    mantis: Mantis[];
    submissionDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mantisConsumptionService: MantisConsumptionService,
        private userService: UserService,
        private mantisService: MantisService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.mantisService.query()
            .subscribe((res: HttpResponse<Mantis[]>) => { this.mantis = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mantisConsumption.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mantisConsumptionService.update(this.mantisConsumption));
        } else {
            this.subscribeToSaveResponse(
                this.mantisConsumptionService.create(this.mantisConsumption));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MantisConsumption>>) {
        result.subscribe((res: HttpResponse<MantisConsumption>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MantisConsumption) {
        this.eventManager.broadcast({ name: 'mantisConsumptionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackMantisById(index: number, item: Mantis) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-mantis-consumption-popup',
    template: ''
})
export class MantisConsumptionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mantisConsumptionPopupService: MantisConsumptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mantisConsumptionPopupService
                    .open(MantisConsumptionDialogComponent as Component, params['id']);
            } else {
                this.mantisConsumptionPopupService
                    .open(MantisConsumptionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
