import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MantisStatus } from './mantis-status.model';
import { MantisStatusService } from './mantis-status.service';

@Component({
    selector: 'jhi-mantis-status-detail',
    templateUrl: './mantis-status-detail.component.html'
})
export class MantisStatusDetailComponent implements OnInit, OnDestroy {

    mantisStatus: MantisStatus;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mantisStatusService: MantisStatusService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMantisStatuses();
    }

    load(id) {
        this.mantisStatusService.find(id)
            .subscribe((mantisStatusResponse: HttpResponse<MantisStatus>) => {
                this.mantisStatus = mantisStatusResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMantisStatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mantisStatusListModification',
            (response) => this.load(this.mantisStatus.id)
        );
    }
}
