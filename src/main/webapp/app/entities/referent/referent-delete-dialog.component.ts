import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Referent } from './referent.model';
import { ReferentPopupService } from './referent-popup.service';
import { ReferentService } from './referent.service';

@Component({
    selector: 'jhi-referent-delete-dialog',
    templateUrl: './referent-delete-dialog.component.html'
})
export class ReferentDeleteDialogComponent {

    referent: Referent;

    constructor(
        private referentService: ReferentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.referentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'referentListModification',
                content: 'Deleted an referent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-referent-delete-popup',
    template: ''
})
export class ReferentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private referentPopupService: ReferentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.referentPopupService
                .open(ReferentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
