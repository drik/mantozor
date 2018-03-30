import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MantisImportComponent } from './mantis-import.component';
import { MantisImportDetailComponent } from './mantis-import-detail.component';
import { MantisImportPopupComponent } from './mantis-import-dialog.component';
import { MantisImportDeletePopupComponent } from './mantis-import-delete-dialog.component';

export const mantisImportRoute: Routes = [
    {
        path: 'mantis-import',
        component: MantisImportComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImport.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mantis-import/:id',
        component: MantisImportDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImport.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mantisImportPopupRoute: Routes = [
    {
        path: 'mantis-import-new',
        component: MantisImportPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-import/:id/edit',
        component: MantisImportPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-import/:id/delete',
        component: MantisImportDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
