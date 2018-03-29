import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { MantisFile } from './mantis-file.model';
import { MantisFileService } from './mantis-file.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-mantis-file',
    templateUrl: './mantis-file.component.html'
})
export class MantisFileComponent implements OnInit, OnDestroy {
mantisFiles: MantisFile[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private mantisFileService: MantisFileService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.mantisFileService.query().subscribe(
            (res: HttpResponse<MantisFile[]>) => {
                this.mantisFiles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMantisFiles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MantisFile) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInMantisFiles() {
        this.eventSubscriber = this.eventManager.subscribe('mantisFileListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
