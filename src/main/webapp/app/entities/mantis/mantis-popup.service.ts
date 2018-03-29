import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Mantis } from './mantis.model';
import { MantisService } from './mantis.service';

@Injectable()
export class MantisPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private mantisService: MantisService

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
                this.mantisService.find(id)
                    .subscribe((mantisResponse: HttpResponse<Mantis>) => {
                        const mantis: Mantis = mantisResponse.body;
                        if (mantis.submissionDate) {
                            mantis.submissionDate = {
                                year: mantis.submissionDate.getFullYear(),
                                month: mantis.submissionDate.getMonth() + 1,
                                day: mantis.submissionDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.mantisModalRef(component, mantis);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.mantisModalRef(component, new Mantis());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    mantisModalRef(component: Component, mantis: Mantis): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.mantis = mantis;
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
