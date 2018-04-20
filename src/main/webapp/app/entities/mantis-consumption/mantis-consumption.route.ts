import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MantisConsumptionComponent } from './mantis-consumption.component';
import { MantisConsumptionDetailComponent } from './mantis-consumption-detail.component';
import { MantisConsumptionPopupComponent } from './mantis-consumption-dialog.component';
import { MantisConsumptionDeletePopupComponent } from './mantis-consumption-delete-dialog.component';

@Injectable()
export class MantisConsumptionResolvePagingParams implements Resolve<any> {

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

export const mantisConsumptionRoute: Routes = [
    {
        path: 'mantis-consumption',
        component: MantisConsumptionComponent,
        resolve: {
            'pagingParams': MantisConsumptionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisConsumption.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mantis-consumption/:id',
        component: MantisConsumptionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisConsumption.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mantisConsumptionPopupRoute: Routes = [
    {
        path: 'mantis-consumption-new/:idMantis',
        component: MantisConsumptionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisConsumption.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-consumption-new',
        component: MantisConsumptionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisConsumption.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-consumption/:id/edit',
        component: MantisConsumptionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisConsumption.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mantis-consumption/:id/delete',
        component: MantisConsumptionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mantozorApp.mantisConsumption.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
