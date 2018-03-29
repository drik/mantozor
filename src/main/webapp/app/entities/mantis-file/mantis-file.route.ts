import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MantisFileComponent } from './mantis-file.component';
import { MantisFileDetailComponent } from './mantis-file-detail.component';
import { MantisFilePopupComponent } from './mantis-file-dialog.component';
import { MantisFileDeletePopupComponent } from './mantis-file-delete-dialog.component';

export const mantisFileRoute: Routes = [
    {
        path: 'mantis-file',
        component: MantisFileComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisFile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mantis-file/:id',
        component: MantisFileDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisFile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mantisFilePopupRoute: Routes = [
    {
        path: 'mantis-file-new',
        component: MantisFilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisFile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-file/:id/edit',
        component: MantisFilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisFile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-file/:id/delete',
        component: MantisFileDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisFile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
