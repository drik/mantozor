import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { MantisConsumption } from './mantis-consumption.model';
import { MantisConsumptionService } from './mantis-consumption.service';

@Injectable()
export class MantisConsumptionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private mantisConsumptionService: MantisConsumptionService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.mantisConsumptionService.find(id)
                    .subscribe((mantisConsumptionResponse: HttpResponse<MantisConsumption>) => {
                        const mantisConsumption: MantisConsumption = mantisConsumptionResponse.body;
                        if (mantisConsumption.submissionDate) {
                            mantisConsumption.submissionDate = {
                                year: mantisConsumption.submissionDate.getFullYear(),
                                month: mantisConsumption.submissionDate.getMonth() + 1,
                                day: mantisConsumption.submissionDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.mantisConsumptionModalRef(component, mantisConsumption);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.mantisConsumptionModalRef(component, new MantisConsumption());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    mantisConsumptionModalRef(component: Component, mantisConsumption: MantisConsumption): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.mantisConsumption = mantisConsumption;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
