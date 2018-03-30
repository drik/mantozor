import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ReferentComponent } from './referent.component';
import { ReferentDetailComponent } from './referent-detail.component';
import { ReferentPopupComponent } from './referent-dialog.component';
import { ReferentDeletePopupComponent } from './referent-delete-dialog.component';

export const referentRoute: Routes = [
    {
        path: 'referent',
        component: ReferentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.referent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'referent/:id',
        component: ReferentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.referent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const referentPopupRoute: Routes = [
    {
        path: 'referent-new',
        component: ReferentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.referent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'referent/:id/edit',
        component: ReferentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.referent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'referent/:id/delete',
        component: ReferentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.referent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
