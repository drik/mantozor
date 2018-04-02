import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MantozorSharedModule } from '../../shared';
import {
    MantisApproverService,
    MantisApproverPopupService,
    MantisApproverComponent,
    MantisApproverDetailComponent,
    MantisApproverDialogComponent,
    MantisApproverPopupComponent,
    MantisApproverDeletePopupComponent,
    MantisApproverDeleteDialogComponent,
    mantisApproverRoute,
    mantisApproverPopupRoute,
    MantisApproverResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mantisApproverRoute,
    ...mantisApproverPopupRoute,
];

@NgModule({
    imports: [
        MantozorSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MantisApproverComponent,
        MantisApproverDetailComponent,
        MantisApproverDialogComponent,
        MantisApproverDeleteDialogComponent,
        MantisApproverPopupComponent,
        MantisApproverDeletePopupComponent,
    ],
    entryComponents: [
        MantisApproverComponent,
        MantisApproverDialogComponent,
        MantisApproverPopupComponent,
        MantisApproverDeleteDialogComponent,
        MantisApproverDeletePopupComponent,
    ],
    providers: [
        MantisApproverService,
        MantisApproverPopupService,
        MantisApproverResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MantozorMantisApproverModule {}
