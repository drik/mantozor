import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MantisComponent } from './mantis.component';
import { MantisDetailComponent } from './mantis-detail.component';
import { MantisPopupComponent } from './mantis-dialog.component';
import { MantisDeletePopupComponent } from './mantis-delete-dialog.component';

@Injectable()
export class MantisResolvePagingParams implements Resolve<any> {

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

export const mantisRoute: Routes = [
    {
        path: 'mantis',
        component: MantisComponent,
        resolve: {
            'pagingParams': MantisResolvePagingParams
        },
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
