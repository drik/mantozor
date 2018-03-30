import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { MantisImportLine } from './mantis-import-line.model';
import { MantisImportLineService } from './mantis-import-line.service';

@Injectable()
export class MantisImportLinePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private mantisImportLineService: MantisImportLineService

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
                this.mantisImportLineService.find(id)
                    .subscribe((mantisImportLineResponse: HttpResponse<MantisImportLine>) => {
                        const mantisImportLine: MantisImportLine = mantisImportLineResponse.body;
                        if (mantisImportLine.updateDate) {
                            mantisImportLine.updateDate = {
                                year: mantisImportLine.updateDate.getFullYear(),
                                month: mantisImportLine.updateDate.getMonth() + 1,
                                day: mantisImportLine.updateDate.getDate()
                            };
                        }
                        if (mantisImportLine.submissionDate) {
                            mantisImportLine.submissionDate = {
                                year: mantisImportLine.submissionDate.getFullYear(),
                                month: mantisImportLine.submissionDate.getMonth() + 1,
                                day: mantisImportLine.submissionDate.getDate()
                            };
                        }
                        if (mantisImportLine.desiredCommitmentDate) {
                            mantisImportLine.desiredCommitmentDate = {
                                year: mantisImportLine.desiredCommitmentDate.getFullYear(),
                                month: mantisImportLine.desiredCommitmentDate.getMonth() + 1,
                                day: mantisImportLine.desiredCommitmentDate.getDate()
                            };
                        }
                        if (mantisImportLine.commitmentDateCDS) {
                            mantisImportLine.commitmentDateCDS = {
                                year: mantisImportLine.commitmentDateCDS.getFullYear(),
                                month: mantisImportLine.commitmentDateCDS.getMonth() + 1,
                                day: mantisImportLine.commitmentDateCDS.getDate()
                            };
                        }
                        if (mantisImportLine.estimatedDSTDelivreryDate) {
                            mantisImportLine.estimatedDSTDelivreryDate = {
                                year: mantisImportLine.estimatedDSTDelivreryDate.getFullYear(),
                                month: mantisImportLine.estimatedDSTDelivreryDate.getMonth() + 1,
                                day: mantisImportLine.estimatedDSTDelivreryDate.getDate()
                            };
                        }
                        if (mantisImportLine.recipeDate) {
                            mantisImportLine.recipeDate = {
                                year: mantisImportLine.recipeDate.getFullYear(),
                                month: mantisImportLine.recipeDate.getMonth() + 1,
                                day: mantisImportLine.recipeDate.getDate()
                            };
                        }
                        if (mantisImportLine.productionDate) {
                            mantisImportLine.productionDate = {
                                year: mantisImportLine.productionDate.getFullYear(),
                                month: mantisImportLine.productionDate.getMonth() + 1,
                                day: mantisImportLine.productionDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.mantisImportLineModalRef(component, mantisImportLine);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.mantisImportLineModalRef(component, new MantisImportLine());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    mantisImportLineModalRef(component: Component, mantisImportLine: MantisImportLine): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.mantisImportLine = mantisImportLine;
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
