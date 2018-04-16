import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MantisConsumption } from './mantis-consumption.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MantisConsumption>;

@Injectable()
export class MantisConsumptionService {

    private resourceUrl =  SERVER_API_URL + 'api/mantis-consumptions';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(mantisConsumption: MantisConsumption): Observable<EntityResponseType> {
        const copy = this.convert(mantisConsumption);
        return this.http.post<MantisConsumption>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(mantisConsumption: MantisConsumption): Observable<EntityResponseType> {
        const copy = this.convert(mantisConsumption);
        return this.http.put<MantisConsumption>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MantisConsumption>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MantisConsumption[]>> {
        const options = createRequestOption(req);
        return this.http.get<MantisConsumption[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MantisConsumption[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MantisConsumption = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MantisConsumption[]>): HttpResponse<MantisConsumption[]> {
        const jsonResponse: MantisConsumption[] = res.body;
        const body: MantisConsumption[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MantisConsumption.
     */
    private convertItemFromServer(mantisConsumption: MantisConsumption): MantisConsumption {
        const copy: MantisConsumption = Object.assign({}, mantisConsumption);
        copy.submissionDate = this.dateUtils
            .convertLocalDateFromServer(mantisConsumption.submissionDate);
        return copy;
    }

    /**
     * Convert a MantisConsumption to a JSON which can be sent to the server.
     */
    private convert(mantisConsumption: MantisConsumption): MantisConsumption {
        const copy: MantisConsumption = Object.assign({}, mantisConsumption);
        copy.submissionDate = this.dateUtils
            .convertLocalDateToServer(mantisConsumption.submissionDate);
        return copy;
    }
}
