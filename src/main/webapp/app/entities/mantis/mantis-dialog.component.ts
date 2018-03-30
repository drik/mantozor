import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Mantis } from './mantis.model';
import { MantisPopupService } from './mantis-popup.service';
import { MantisService } from './mantis.service';
import { Project, ProjectService } from '../project';

@Component({
    selector: 'jhi-mantis-dialog',
    templateUrl: './mantis-dialog.component.html'
})
export class MantisDialogComponent implements OnInit {

    mantis: Mantis;
    isSaving: boolean;

    projects: Project[];
    submissionDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mantisService: MantisService,
        private projectService: ProjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.projectService.query()
            .subscribe((res: HttpResponse<Project[]>) => { this.projects = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mantis.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mantisService.update(this.mantis));
        } else {
            this.subscribeToSaveResponse(
                this.mantisService.create(this.mantis));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Mantis>>) {
        result.subscribe((res: HttpResponse<Mantis>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Mantis) {
        this.eventManager.broadcast({ name: 'mantisListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProjectById(index: number, item: Project) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-mantis-popup',
    template: ''
})
export class MantisPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mantisPopupService: MantisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mantisPopupService
                    .open(MantisDialogComponent as Component, params['id']);
            } else {
                this.mantisPopupService
                    .open(MantisDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
