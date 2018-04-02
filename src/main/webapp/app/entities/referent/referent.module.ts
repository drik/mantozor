import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MantozorSharedModule } from '../../shared';
import {
    ReferentService,
    ReferentPopupService,
    ReferentComponent,
    ReferentDetailComponent,
    ReferentDialogComponent,
    ReferentPopupComponent,
    ReferentDeletePopupComponent,
    ReferentDeleteDialogComponent,
    referentRoute,
    referentPopupRoute,
    ReferentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...referentRoute,
    ...referentPopupRoute,
];

@NgModule({
    imports: [
        MantozorSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ReferentComponent,
        ReferentDetailComponent,
        ReferentDialogComponent,
        ReferentDeleteDialogComponent,
        ReferentPopupComponent,
        ReferentDeletePopupComponent,
    ],
    entryComponents: [
        ReferentComponent,
        ReferentDialogComponent,
        ReferentPopupComponent,
        ReferentDeleteDialogComponent,
        ReferentDeletePopupComponent,
    ],
    providers: [
        ReferentService,
        ReferentPopupService,
        ReferentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MantozorReferentModule {}
