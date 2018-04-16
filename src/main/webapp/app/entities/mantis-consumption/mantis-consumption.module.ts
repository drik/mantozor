import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MantozorSharedModule } from '../../shared';
import { MantozorAdminModule } from '../../admin/admin.module';
import {
    MantisConsumptionService,
    MantisConsumptionPopupService,
    MantisConsumptionComponent,
    MantisConsumptionDetailComponent,
    MantisConsumptionDialogComponent,
    MantisConsumptionPopupComponent,
    MantisConsumptionDeletePopupComponent,
    MantisConsumptionDeleteDialogComponent,
    mantisConsumptionRoute,
    mantisConsumptionPopupRoute,
    MantisConsumptionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mantisConsumptionRoute,
    ...mantisConsumptionPopupRoute,
];

@NgModule({
    imports: [
        MantozorSharedModule,
        MantozorAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MantisConsumptionComponent,
        MantisConsumptionDetailComponent,
        MantisConsumptionDialogComponent,
        MantisConsumptionDeleteDialogComponent,
        MantisConsumptionPopupComponent,
        MantisConsumptionDeletePopupComponent,
    ],
    entryComponents: [
        MantisConsumptionComponent,
        MantisConsumptionDialogComponent,
        MantisConsumptionPopupComponent,
        MantisConsumptionDeleteDialogComponent,
        MantisConsumptionDeletePopupComponent,
    ],
    providers: [
        MantisConsumptionService,
        MantisConsumptionPopupService,
        MantisConsumptionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MantozorMantisConsumptionModule {}
