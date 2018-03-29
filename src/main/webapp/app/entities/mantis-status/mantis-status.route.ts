import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MantisStatusComponent } from './mantis-status.component';
import { MantisStatusDetailComponent } from './mantis-status-detail.component';
import { MantisStatusPopupComponent } from './mantis-status-dialog.component';
import { MantisStatusDeletePopupComponent } from './mantis-status-delete-dialog.component';

@Injectable()
export class MantisStatusResolvePagingParams implements Resolve<any> {

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

export const mantisStatusRoute: Routes = [
    {
        path: 'mantis-status',
        component: MantisStatusComponent,
        resolve: {
            'pagingParams': MantisStatusResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mantis-status/:id',
        component: MantisStatusDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mantisStatusPopupRoute: Routes = [
    {
        path: 'mantis-status-new',
        component: MantisStatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-status/:id/edit',
        component: MantisStatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-status/:id/delete',
        component: MantisStatusDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
