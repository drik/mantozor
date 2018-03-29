import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MantisFile } from './mantis-file.model';
import { MantisFilePopupService } from './mantis-file-popup.service';
import { MantisFileService } from './mantis-file.service';

@Component({
    selector: 'jhi-mantis-file-delete-dialog',
    templateUrl: './mantis-file-delete-dialog.component.html'
})
export class MantisFileDeleteDialogComponent {

    mantisFile: MantisFile;

    constructor(
        private mantisFileService: MantisFileService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mantisFileService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mantisFileListModification',
                content: 'Deleted an mantisFile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mantis-file-delete-popup',
    template: ''
})
export class MantisFileDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mantisFilePopupService: MantisFilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mantisFilePopupService
                .open(MantisFileDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
