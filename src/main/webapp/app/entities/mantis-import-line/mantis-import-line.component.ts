import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MantisImportLine } from './mantis-import-line.model';
import { MantisImportLineService } from './mantis-import-line.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-mantis-import-line',
    templateUrl: './mantis-import-line.component.html'
})
export class MantisImportLineComponent implements OnInit, OnDestroy {
mantisImportLines: MantisImportLine[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private mantisImportLineService: MantisImportLineService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.mantisImportLineService.query().subscribe(
            (res: HttpResponse<MantisImportLine[]>) => {
                this.mantisImportLines = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMantisImportLines();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MantisImportLine) {
        return item.id;
    }
    registerChangeInMantisImportLines() {
        this.eventSubscriber = this.eventManager.subscribe('mantisImportLineListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
