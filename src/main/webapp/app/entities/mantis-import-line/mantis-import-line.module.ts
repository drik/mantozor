import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MantozorSharedModule } from '../../shared';
import {
    MantisImportLineService,
    MantisImportLinePopupService,
    MantisImportLineComponent,
    MantisImportLineDetailComponent,
    MantisImportLineDialogComponent,
    MantisImportLinePopupComponent,
    MantisImportLineDeletePopupComponent,
    MantisImportLineDeleteDialogComponent,
    mantisImportLineRoute,
    mantisImportLinePopupRoute,
    MantisImportLineResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mantisImportLineRoute,
    ...mantisImportLinePopupRoute,
];

@NgModule({
    imports: [
        MantozorSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MantisImportLineComponent,
        MantisImportLineDetailComponent,
        MantisImportLineDialogComponent,
        MantisImportLineDeleteDialogComponent,
        MantisImportLinePopupComponent,
        MantisImportLineDeletePopupComponent,
    ],
    entryComponents: [
        MantisImportLineComponent,
        MantisImportLineDialogComponent,
        MantisImportLinePopupComponent,
        MantisImportLineDeleteDialogComponent,
        MantisImportLineDeletePopupComponent,
    ],
    providers: [
        MantisImportLineService,
        MantisImportLinePopupService,
        MantisImportLineResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MantozorMantisImportLineModule {}
