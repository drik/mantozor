import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MantisApproverComponent } from './mantis-approver.component';
import { MantisApproverDetailComponent } from './mantis-approver-detail.component';
import { MantisApproverPopupComponent } from './mantis-approver-dialog.component';
import { MantisApproverDeletePopupComponent } from './mantis-approver-delete-dialog.component';

@Injectable()
export class MantisApproverResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const mantisApproverRoute: Routes = [
    {
        path: 'mantis-approver',
        component: MantisApproverComponent,
        resolve: {
            'pagingParams': MantisApproverResolvePagingParams
        },
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
