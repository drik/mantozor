import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MantozorSharedModule } from '../../shared';
import {
    MantisService,
    MantisPopupService,
    MantisComponent,
    MantisDetailComponent,
    MantisDialogComponent,
    MantisPopupComponent,
    MantisDeletePopupComponent,
    MantisDeleteDialogComponent,
    mantisRoute,
    mantisPopupRoute,
} from './';

const ENTITY_STATES = [
    ...mantisRoute,
    ...mantisPopupRoute,
];

@NgModule({
    imports: [
        MantozorSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MantisComponent,
        MantisDetailComponent,
        MantisDialogComponent,
        MantisDeleteDialogComponent,
        MantisPopupComponent,
        MantisDeletePopupComponent,
    ],
    entryComponents: [
        MantisComponent,
        MantisDialogComponent,
        MantisPopupComponent,
        MantisDeleteDialogComponent,
        MantisDeletePopupComponent,
    ],
    providers: [
        MantisService,
        MantisPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MantozorMantisModule {}
