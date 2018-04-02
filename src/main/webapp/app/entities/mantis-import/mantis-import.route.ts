import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MantisImportComponent } from './mantis-import.component';
import { MantisImportDetailComponent } from './mantis-import-detail.component';
import { MantisImportPopupComponent } from './mantis-import-dialog.component';
import { MantisImportDeletePopupComponent } from './mantis-import-delete-dialog.component';

@Injectable()
export class MantisImportResolvePagingParams implements Resolve<any> {

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

export const mantisImportRoute: Routes = [
    {
        path: 'mantis-import',
        component: MantisImportComponent,
        resolve: {
            'pagingParams': MantisImportResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImport.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mantis-import/:id',
        component: MantisImportDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImport.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mantisImportPopupRoute: Routes = [
    {
        path: 'mantis-import-new',
        component: MantisImportPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-import/:id/edit',
        component: MantisImportPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-import/:id/delete',
        component: MantisImportDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
