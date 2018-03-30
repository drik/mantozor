import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { MantisImport } from './mantis-import.model';
import { MantisImportService } from './mantis-import.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-mantis-import',
    templateUrl: './mantis-import.component.html'
})
export class MantisImportComponent implements OnInit, OnDestroy {
mantisImports: MantisImport[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private mantisImportService: MantisImportService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.mantisImportService.query().subscribe(
            (res: HttpResponse<MantisImport[]>) => {
                this.mantisImports = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMantisImports();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MantisImport) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInMantisImports() {
        this.eventSubscriber = this.eventManager.subscribe('mantisImportListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
