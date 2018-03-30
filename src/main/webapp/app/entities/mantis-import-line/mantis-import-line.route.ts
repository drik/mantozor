import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MantisImportLineComponent } from './mantis-import-line.component';
import { MantisImportLineDetailComponent } from './mantis-import-line-detail.component';
import { MantisImportLinePopupComponent } from './mantis-import-line-dialog.component';
import { MantisImportLineDeletePopupComponent } from './mantis-import-line-delete-dialog.component';

export const mantisImportLineRoute: Routes = [
    {
        path: 'mantis-import-line',
        component: MantisImportLineComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImportLine.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mantis-import-line/:id',
        component: MantisImportLineDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImportLine.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mantisImportLinePopupRoute: Routes = [
    {
        path: 'mantis-import-line-new',
        component: MantisImportLinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImportLine.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-import-line/:id/edit',
        component: MantisImportLinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImportLine.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-import-line/:id/delete',
        component: MantisImportLineDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImportLine.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
