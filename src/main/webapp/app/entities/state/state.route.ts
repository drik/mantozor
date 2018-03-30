import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { StateComponent } from './state.component';
import { StateDetailComponent } from './state-detail.component';
import { StatePopupComponent } from './state-dialog.component';
import { StateDeletePopupComponent } from './state-delete-dialog.component';

export const stateRoute: Routes = [
    {
        path: 'state',
        component: StateComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.state.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'state/:id',
        component: StateDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.state.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const statePopupRoute: Routes = [
    {
        path: 'state-new',
        component: StatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.state.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'state/:id/edit',
        component: StatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.state.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'state/:id/delete',
        component: StateDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.state.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
