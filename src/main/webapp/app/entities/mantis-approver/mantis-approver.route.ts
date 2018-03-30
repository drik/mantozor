import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MantisApproverComponent } from './mantis-approver.component';
import { MantisApproverDetailComponent } from './mantis-approver-detail.component';
import { MantisApproverPopupComponent } from './mantis-approver-dialog.component';
import { MantisApproverDeletePopupComponent } from './mantis-approver-delete-dialog.component';

export const mantisApproverRoute: Routes = [
    {
        path: 'mantis-approver',
        component: MantisApproverComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisApprover.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mantis-approver/:id',
        component: MantisApproverDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisApprover.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mantisApproverPopupRoute: Routes = [
    {
        path: 'mantis-approver-new',
        component: MantisApproverPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisApprover.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-approver/:id/edit',
        component: MantisApproverPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisApprover.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-approver/:id/delete',
        component: MantisApproverDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisApprover.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
