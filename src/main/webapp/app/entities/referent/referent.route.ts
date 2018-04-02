import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ReferentComponent } from './referent.component';
import { ReferentDetailComponent } from './referent-detail.component';
import { ReferentPopupComponent } from './referent-dialog.component';
import { ReferentDeletePopupComponent } from './referent-delete-dialog.component';

@Injectable()
export class ReferentResolvePagingParams implements Resolve<any> {

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

export const referentRoute: Routes = [
    {
        path: 'referent',
        component: ReferentComponent,
        resolve: {
            'pagingParams': ReferentResolvePagingParams
        },
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
