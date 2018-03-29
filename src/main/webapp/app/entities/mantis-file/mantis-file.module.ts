import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MantozorSharedModule } from '../../shared';
import {
    MantisFileService,
    MantisFilePopupService,
    MantisFileComponent,
    MantisFileDetailComponent,
    MantisFileDialogComponent,
    MantisFilePopupComponent,
    MantisFileDeletePopupComponent,
    MantisFileDeleteDialogComponent,
    mantisFileRoute,
    mantisFilePopupRoute,
} from './';

const ENTITY_STATES = [
    ...mantisFileRoute,
    ...mantisFilePopupRoute,
];

@NgModule({
    imports: [
        MantozorSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MantisFileComponent,
        MantisFileDetailComponent,
        MantisFileDialogComponent,
        MantisFileDeleteDialogComponent,
        MantisFilePopupComponent,
        MantisFileDeletePopupComponent,
    ],
    entryComponents: [
        MantisFileComponent,
        MantisFileDialogComponent,
        MantisFilePopupComponent,
        MantisFileDeleteDialogComponent,
        MantisFileDeletePopupComponent,
    ],
    providers: [
        MantisFileService,
        MantisFilePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MantozorMantisFileModule {}
