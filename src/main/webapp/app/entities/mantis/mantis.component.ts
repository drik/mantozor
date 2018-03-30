import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Mantis } from './mantis.model';
import { MantisService } from './mantis.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-mantis',
    templateUrl: './mantis.component.html'
})
export class MantisComponent implements OnInit, OnDestroy {
mantis: Mantis[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private mantisService: MantisService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.mantisService.query().subscribe(
            (res: HttpResponse<Mantis[]>) => {
                this.mantis = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMantis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Mantis) {
        return item.id;
    }
    registerChangeInMantis() {
        this.eventSubscriber = this.eventManager.subscribe('mantisListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
