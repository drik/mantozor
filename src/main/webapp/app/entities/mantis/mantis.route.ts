import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MantisComponent } from './mantis.component';
import { MantisDetailComponent } from './mantis-detail.component';
import { MantisPopupComponent } from './mantis-dialog.component';
import { MantisDeletePopupComponent } from './mantis-delete-dialog.component';

export const mantisRoute: Routes = [
    {
        path: 'mantis',
        component: MantisComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mantis/:id',
        component: MantisDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mantisPopupRoute: Routes = [
    {
        path: 'mantis-new',
        component: MantisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis/:id/edit',
        component: MantisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis/:id/delete',
        component: MantisDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
