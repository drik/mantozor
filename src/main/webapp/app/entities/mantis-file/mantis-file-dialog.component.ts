import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { MantisFile } from './mantis-file.model';
import { MantisFilePopupService } from './mantis-file-popup.service';
import { MantisFileService } from './mantis-file.service';

@Component({
    selector: 'jhi-mantis-file-dialog',
    templateUrl: './mantis-file-dialog.component.html'
})
export class MantisFileDialogComponent implements OnInit {

    mantisFile: MantisFile;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private mantisFileService: MantisFileService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mantisFile.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mantisFileService.update(this.mantisFile));
        } else {
            this.subscribeToSaveResponse(
                this.mantisFileService.create(this.mantisFile));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MantisFile>>) {
        result.subscribe((res: HttpResponse<MantisFile>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MantisFile) {
        this.eventManager.broadcast({ name: 'mantisFileListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-mantis-file-popup',
    template: ''
})
export class MantisFilePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mantisFilePopupService: MantisFilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mantisFilePopupService
                    .open(MantisFileDialogComponent as Component, params['id']);
            } else {
                this.mantisFilePopupService
                    .open(MantisFileDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
