import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Mantis } from './mantis.model';
import { MantisService } from './mantis.service';

@Component({
    selector: 'jhi-mantis-detail',
    templateUrl: './mantis-detail.component.html'
})
export class MantisDetailComponent implements OnInit, OnDestroy {

    mantis: Mantis;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mantisService: MantisService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMantis();
    }

    load(id) {
        this.mantisService.find(id)
            .subscribe((mantisResponse: HttpResponse<Mantis>) => {
                this.mantis = mantisResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMantis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mantisListModification',
            (response) => this.load(this.mantis.id)
        );
    }
}
