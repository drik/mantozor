import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MantisImport } from './mantis-import.model';
import { MantisImportPopupService } from './mantis-import-popup.service';
import { MantisImportService } from './mantis-import.service';

@Component({
    selector: 'jhi-mantis-import-delete-dialog',
    templateUrl: './mantis-import-delete-dialog.component.html'
})
export class MantisImportDeleteDialogComponent {

    mantisImport: MantisImport;

    constructor(
        private mantisImportService: MantisImportService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mantisImportService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mantisImportListModification',
                content: 'Deleted an mantisImport'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mantis-import-delete-popup',
    template: ''
})
export class MantisImportDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mantisImportPopupService: MantisImportPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mantisImportPopupService
                .open(MantisImportDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
