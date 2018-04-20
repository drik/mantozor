import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { MantisService } from '../entities/mantis';

import { Account, LoginModalService, Principal } from '../shared';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    statusStats: any[];
    statusStateStats: any[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private mantisService: MantisService
    ) {
    }

    ngOnInit() {
        this.mantisService.getStatsForStatus()
            .subscribe((res: HttpResponse<any[]>) => { this.statusStats = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));

        this.mantisService.getStatsForStatusAndState()
            .subscribe((res: HttpResponse<any[]>) => { this.statusStateStats = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));

        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
