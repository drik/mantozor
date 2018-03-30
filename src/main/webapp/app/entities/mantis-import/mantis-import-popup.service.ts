import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { MantisImport } from './mantis-import.model';
import { MantisImportService } from './mantis-import.service';

@Injectable()
export class MantisImportPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private mantisImportService: MantisImportService

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
                this.mantisImportService.find(id)
                    .subscribe((mantisImportResponse: HttpResponse<MantisImport>) => {
                        const mantisImport: MantisImport = mantisImportResponse.body;
                        if (mantisImport.importDate) {
                            mantisImport.importDate = {
                                year: mantisImport.importDate.getFullYear(),
                                month: mantisImport.importDate.getMonth() + 1,
                                day: mantisImport.importDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.mantisImportModalRef(component, mantisImport);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.mantisImportModalRef(component, new MantisImport());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    mantisImportModalRef(component: Component, mantisImport: MantisImport): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.mantisImport = mantisImport;
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
