import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MantozorSharedModule } from '../../shared';
import { MantozorAdminModule } from '../../admin/admin.module';
import {
    MantisImportService,
    MantisImportPopupService,
    MantisImportComponent,
    MantisImportDetailComponent,
    MantisImportDialogComponent,
    MantisImportPopupComponent,
    MantisImportDeletePopupComponent,
    MantisImportDeleteDialogComponent,
    mantisImportRoute,
    mantisImportPopupRoute,
    MantisImportResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mantisImportRoute,
    ...mantisImportPopupRoute,
];

@NgModule({
    imports: [
        MantozorSharedModule,
        MantozorAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MantisImportComponent,
        MantisImportDetailComponent,
        MantisImportDialogComponent,
        MantisImportDeleteDialogComponent,
        MantisImportPopupComponent,
        MantisImportDeletePopupComponent,
    ],
    entryComponents: [
        MantisImportComponent,
        MantisImportDialogComponent,
        MantisImportPopupComponent,
        MantisImportDeleteDialogComponent,
        MantisImportDeletePopupComponent,
    ],
    providers: [
        MantisImportService,
        MantisImportPopupService,
        MantisImportResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MantozorMantisImportModule {}
