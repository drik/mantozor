import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MantisImportLine } from './mantis-import-line.model';
import { MantisImportLinePopupService } from './mantis-import-line-popup.service';
import { MantisImportLineService } from './mantis-import-line.service';

@Component({
    selector: 'jhi-mantis-import-line-delete-dialog',
    templateUrl: './mantis-import-line-delete-dialog.component.html'
})
export class MantisImportLineDeleteDialogComponent {

    mantisImportLine: MantisImportLine;

    constructor(
        private mantisImportLineService: MantisImportLineService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mantisImportLineService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mantisImportLineListModification',
                content: 'Deleted an mantisImportLine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mantis-import-line-delete-popup',
    template: ''
})
export class MantisImportLineDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mantisImportLinePopupService: MantisImportLinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mantisImportLinePopupService
                .open(MantisImportLineDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
