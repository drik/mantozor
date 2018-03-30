import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Mantis } from './mantis.model';
import { MantisPopupService } from './mantis-popup.service';
import { MantisService } from './mantis.service';

@Component({
    selector: 'jhi-mantis-delete-dialog',
    templateUrl: './mantis-delete-dialog.component.html'
})
export class MantisDeleteDialogComponent {

    mantis: Mantis;

    constructor(
        private mantisService: MantisService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mantisService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mantisListModification',
                content: 'Deleted an mantis'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mantis-delete-popup',
    template: ''
})
export class MantisDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mantisPopupService: MantisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mantisPopupService
                .open(MantisDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
