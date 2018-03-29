import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { MantisFile } from './mantis-file.model';
import { MantisFileService } from './mantis-file.service';

@Component({
    selector: 'jhi-mantis-file-detail',
    templateUrl: './mantis-file-detail.component.html'
})
export class MantisFileDetailComponent implements OnInit, OnDestroy {

    mantisFile: MantisFile;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private mantisFileService: MantisFileService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMantisFiles();
    }

    load(id) {
        this.mantisFileService.find(id)
            .subscribe((mantisFileResponse: HttpResponse<MantisFile>) => {
                this.mantisFile = mantisFileResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMantisFiles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mantisFileListModification',
            (response) => this.load(this.mantisFile.id)
        );
    }
}
