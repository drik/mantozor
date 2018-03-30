import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MantisApprover } from './mantis-approver.model';
import { MantisApproverPopupService } from './mantis-approver-popup.service';
import { MantisApproverService } from './mantis-approver.service';

@Component({
    selector: 'jhi-mantis-approver-delete-dialog',
    templateUrl: './mantis-approver-delete-dialog.component.html'
})
export class MantisApproverDeleteDialogComponent {

    mantisApprover: MantisApprover;

    constructor(
        private mantisApproverService: MantisApproverService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mantisApproverService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mantisApproverListModification',
                content: 'Deleted an mantisApprover'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mantis-approver-delete-popup',
    template: ''
})
export class MantisApproverDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mantisApproverPopupService: MantisApproverPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mantisApproverPopupService
                .open(MantisApproverDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
