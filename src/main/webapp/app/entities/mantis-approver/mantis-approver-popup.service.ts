import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { MantisApprover } from './mantis-approver.model';
import { MantisApproverService } from './mantis-approver.service';

@Injectable()
export class MantisApproverPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private mantisApproverService: MantisApproverService

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
                this.mantisApproverService.find(id)
                    .subscribe((mantisApproverResponse: HttpResponse<MantisApprover>) => {
                        const mantisApprover: MantisApprover = mantisApproverResponse.body;
                        this.ngbModalRef = this.mantisApproverModalRef(component, mantisApprover);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.mantisApproverModalRef(component, new MantisApprover());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    mantisApproverModalRef(component: Component, mantisApprover: MantisApprover): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.mantisApprover = mantisApprover;
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
