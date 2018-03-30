import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Referent } from './referent.model';
import { ReferentService } from './referent.service';

@Component({
    selector: 'jhi-referent-detail',
    templateUrl: './referent-detail.component.html'
})
export class ReferentDetailComponent implements OnInit, OnDestroy {

    referent: Referent;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private referentService: ReferentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReferents();
    }

    load(id) {
        this.referentService.find(id)
            .subscribe((referentResponse: HttpResponse<Referent>) => {
                this.referent = referentResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReferents() {
        this.eventSubscriber = this.eventManager.subscribe(
            'referentListModification',
            (response) => this.load(this.referent.id)
        );
    }
}
