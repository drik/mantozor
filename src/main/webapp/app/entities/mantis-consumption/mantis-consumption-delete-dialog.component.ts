import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MantisConsumption } from './mantis-consumption.model';
import { MantisConsumptionPopupService } from './mantis-consumption-popup.service';
import { MantisConsumptionService } from './mantis-consumption.service';

@Component({
    selector: 'jhi-mantis-consumption-delete-dialog',
    templateUrl: './mantis-consumption-delete-dialog.component.html'
})
export class MantisConsumptionDeleteDialogComponent {

    mantisConsumption: MantisConsumption;

    constructor(
        private mantisConsumptionService: MantisConsumptionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mantisConsumptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mantisConsumptionListModification',
                content: 'Deleted an mantisConsumption'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mantis-consumption-delete-popup',
    template: ''
})
export class MantisConsumptionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mantisConsumptionPopupService: MantisConsumptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mantisConsumptionPopupService
                .open(MantisConsumptionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
