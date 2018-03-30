import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MantozorSharedModule } from '../../shared';
import {
    StateService,
    StatePopupService,
    StateComponent,
    StateDetailComponent,
    StateDialogComponent,
    StatePopupComponent,
    StateDeletePopupComponent,
    StateDeleteDialogComponent,
    stateRoute,
    statePopupRoute,
} from './';

const ENTITY_STATES = [
    ...stateRoute,
    ...statePopupRoute,
];

@NgModule({
    imports: [
        MantozorSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        StateComponent,
        StateDetailComponent,
        StateDialogComponent,
        StateDeleteDialogComponent,
        StatePopupComponent,
        StateDeletePopupComponent,
    ],
    entryComponents: [
        StateComponent,
        StateDialogComponent,
        StatePopupComponent,
        StateDeleteDialogComponent,
        StateDeletePopupComponent,
    ],
    providers: [
        StateService,
        StatePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MantozorStateModule {}
