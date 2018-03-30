import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { MantisImport } from './mantis-import.model';
import { MantisImportService } from './mantis-import.service';

@Component({
    selector: 'jhi-mantis-import-detail',
    templateUrl: './mantis-import-detail.component.html'
})
export class MantisImportDetailComponent implements OnInit, OnDestroy {

    mantisImport: MantisImport;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private mantisImportService: MantisImportService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMantisImports();
    }

    load(id) {
        this.mantisImportService.find(id)
            .subscribe((mantisImportResponse: HttpResponse<MantisImport>) => {
                this.mantisImport = mantisImportResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMantisImports() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mantisImportListModification',
            (response) => this.load(this.mantisImport.id)
        );
    }
}
