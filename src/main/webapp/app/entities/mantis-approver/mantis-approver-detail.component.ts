import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MantisApprover } from './mantis-approver.model';
import { MantisApproverService } from './mantis-approver.service';

@Component({
    selector: 'jhi-mantis-approver-detail',
    templateUrl: './mantis-approver-detail.component.html'
})
export class MantisApproverDetailComponent implements OnInit, OnDestroy {

    mantisApprover: MantisApprover;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mantisApproverService: MantisApproverService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMantisApprovers();
    }

    load(id) {
        this.mantisApproverService.find(id)
            .subscribe((mantisApproverResponse: HttpResponse<MantisApprover>) => {
                this.mantisApprover = mantisApproverResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMantisApprovers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mantisApproverListModification',
            (response) => this.load(this.mantisApprover.id)
        );
    }
}
