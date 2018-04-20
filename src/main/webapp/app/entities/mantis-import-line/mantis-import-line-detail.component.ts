import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MantisImportLine } from './mantis-import-line.model';
import { MantisImportLineService } from './mantis-import-line.service';
import { MantisService } from '../mantis/mantis.service';

@Component({
    selector: 'jhi-mantis-import-line-detail',
    templateUrl: './mantis-import-line-detail.component.html'
})
export class MantisImportLineDetailComponent implements OnInit, OnDestroy {

    mantisImportLine: MantisImportLine;
        
    timelineItems: any[];
    
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private mantisService: MantisService,
        private mantisImportLineService: MantisImportLineService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMantisImportLines();
    }

    load(id) {
        this.mantisImportLineService.find(id)
            .subscribe((mantisImportLineResponse: HttpResponse<MantisImportLine>) => {
                this.mantisImportLine = mantisImportLineResponse.body;
                
                this.mantisService.getTimeline(this.mantisImportLine.mantisId)
		            .subscribe((res: HttpResponse<any[]>) => { 
		            	this.timelineItems = res.body; 
		         });
            });
            
         
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMantisImportLines() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mantisImportLineListModification',
            (response) => this.load(this.mantisImportLine.id)
        );
    }
}
