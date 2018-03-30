import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MantisStatus } from './mantis-status.model';
import { MantisStatusPopupService } from './mantis-status-popup.service';
import { MantisStatusService } from './mantis-status.service';

@Component({
    selector: 'jhi-mantis-status-delete-dialog',
    templateUrl: './mantis-status-delete-dialog.component.html'
})
export class MantisStatusDeleteDialogComponent {

    mantisStatus: MantisStatus;

    constructor(
        private mantisStatusService: MantisStatusService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mantisStatusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mantisStatusListModification',
                content: 'Deleted an mantisStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mantis-status-delete-popup',
    template: ''
})
export class MantisStatusDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mantisStatusPopupService: MantisStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mantisStatusPopupService
                .open(MantisStatusDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
