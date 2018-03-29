import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MantozorMantisFileModule } from './mantis-file/mantis-file.module';
import { MantozorMantisModule } from './mantis/mantis.module';
import { MantozorProjectModule } from './project/project.module';
import { MantozorReferentModule } from './referent/referent.module';
import { MantozorMantisStatusModule } from './mantis-status/mantis-status.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MantozorMantisFileModule,
        MantozorMantisModule,
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
