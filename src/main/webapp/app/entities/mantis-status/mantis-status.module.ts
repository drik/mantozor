import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MantozorSharedModule } from '../../shared';
import { MantozorAdminModule } from '../../admin/admin.module';
import {
    MantisStatusService,
    MantisStatusPopupService,
    MantisStatusComponent,
    MantisStatusDetailComponent,
    MantisStatusDialogComponent,
    MantisStatusPopupComponent,
    MantisStatusDeletePopupComponent,
    MantisStatusDeleteDialogComponent,
    mantisStatusRoute,
    mantisStatusPopupRoute,
    MantisStatusResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mantisStatusRoute,
    ...mantisStatusPopupRoute,
];

@NgModule({
    imports: [
        MantozorSharedModule,
        MantozorAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MantisStatusComponent,
        MantisStatusDetailComponent,
        MantisStatusDialogComponent,
        MantisStatusDeleteDialogComponent,
        MantisStatusPopupComponent,
        MantisStatusDeletePopupComponent,
    ],
    entryComponents: [
        MantisStatusComponent,
        MantisStatusDialogComponent,
        MantisStatusPopupComponent,
        MantisStatusDeleteDialogComponent,
        MantisStatusDeletePopupComponent,
    ],
    providers: [
        MantisStatusService,
        MantisStatusPopupService,
        MantisStatusResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MantozorMantisStatusModule {}
