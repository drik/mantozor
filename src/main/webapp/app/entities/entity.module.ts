import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MantozorMantisImportModule } from './mantis-import/mantis-import.module';
import { MantozorMantisApproverModule } from './mantis-approver/mantis-approver.module';
import { MantozorStateModule } from './state/state.module';
import { MantozorStatusModule } from './status/status.module';
import { MantozorMantisModule } from './mantis/mantis.module';
import { MantozorMantisImportLineModule } from './mantis-import-line/mantis-import-line.module';
import { MantozorProjectModule } from './project/project.module';
import { MantozorReferentModule } from './referent/referent.module';
import { MantozorMantisStatusModule } from './mantis-status/mantis-status.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MantozorMantisImportModule,
        MantozorMantisApproverModule,
        MantozorStateModule,
        MantozorStatusModule,
        MantozorMantisModule,
        MantozorMantisImportLineModule,
        MantozorProjectModule,
        MantozorReferentModule,
        MantozorMantisStatusModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MantozorEntityModule {}
