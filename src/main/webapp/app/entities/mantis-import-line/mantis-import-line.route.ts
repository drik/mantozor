import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MantisImportLineComponent } from './mantis-import-line.component';
import { MantisImportLineDetailComponent } from './mantis-import-line-detail.component';
import { MantisImportLinePopupComponent } from './mantis-import-line-dialog.component';
import { MantisImportLineDeletePopupComponent } from './mantis-import-line-delete-dialog.component';

@Injectable()
export class MantisImportLineResolvePagingParams implements Resolve<any> {

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

export const mantisImportLineRoute: Routes = [
    {
        path: 'mantis-import-line',
        component: MantisImportLineComponent,
        resolve: {
            'pagingParams': MantisImportLineResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImportLine.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mantis-import-line/:id',
        component: MantisImportLineDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImportLine.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mantisImportLinePopupRoute: Routes = [
    {
        path: 'mantis-import-line-new',
        component: MantisImportLinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImportLine.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-import-line/:id/edit',
        component: MantisImportLinePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImportLine.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-import-line/:id/delete',
        component: MantisImportLineDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisImportLine.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
