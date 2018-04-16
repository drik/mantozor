import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MantisConsumption } from './mantis-consumption.model';
import { MantisConsumptionService } from './mantis-consumption.service';

@Component({
    selector: 'jhi-mantis-consumption-detail',
    templateUrl: './mantis-consumption-detail.component.html'
})
export class MantisConsumptionDetailComponent implements OnInit, OnDestroy {

    mantisConsumption: MantisConsumption;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mantisConsumptionService: MantisConsumptionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMantisConsumptions();
    }

    load(id) {
        this.mantisConsumptionService.find(id)
            .subscribe((mantisConsumptionResponse: HttpResponse<MantisConsumption>) => {
                this.mantisConsumption = mantisConsumptionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMantisConsumptions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mantisConsumptionListModification',
            (response) => this.load(this.mantisConsumption.id)
        );
    }
}
