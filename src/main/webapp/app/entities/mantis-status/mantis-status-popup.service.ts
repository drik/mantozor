import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { MantisStatus } from './mantis-status.model';
import { MantisStatusService } from './mantis-status.service';

@Injectable()
export class MantisStatusPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private mantisStatusService: MantisStatusService

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
                this.mantisStatusService.find(id)
                    .subscribe((mantisStatusResponse: HttpResponse<MantisStatus>) => {
                        const mantisStatus: MantisStatus = mantisStatusResponse.body;
                        if (mantisStatus.changeDate) {
                            mantisStatus.changeDate = {
                                year: mantisStatus.changeDate.getFullYear(),
                                month: mantisStatus.changeDate.getMonth() + 1,
                                day: mantisStatus.changeDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.mantisStatusModalRef(component, mantisStatus);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.mantisStatusModalRef(component, new MantisStatus());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    mantisStatusModalRef(component: Component, mantisStatus: MantisStatus): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.mantisStatus = mantisStatus;
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
