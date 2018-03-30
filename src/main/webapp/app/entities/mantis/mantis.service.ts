import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Mantis } from './mantis.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Mantis>;

@Injectable()
export class MantisService {

    private resourceUrl =  SERVER_API_URL + 'api/mantis';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(mantis: Mantis): Observable<EntityResponseType> {
        const copy = this.convert(mantis);
        return this.http.post<Mantis>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(mantis: Mantis): Observable<EntityResponseType> {
        const copy = this.convert(mantis);
        return this.http.put<Mantis>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Mantis>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Mantis[]>> {
        const options = createRequestOption(req);
        return this.http.get<Mantis[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Mantis[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Mantis = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Mantis[]>): HttpResponse<Mantis[]> {
        const jsonResponse: Mantis[] = res.body;
        const body: Mantis[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Mantis.
     */
    private convertItemFromServer(mantis: Mantis): Mantis {
        const copy: Mantis = Object.assign({}, mantis);
        copy.submissionDate = this.dateUtils
            .convertLocalDateFromServer(mantis.submissionDate);
        return copy;
    }

    /**
     * Convert a Mantis to a JSON which can be sent to the server.
     */
    private convert(mantis: Mantis): Mantis {
        const copy: Mantis = Object.assign({}, mantis);
        copy.submissionDate = this.dateUtils
            .convertLocalDateToServer(mantis.submissionDate);
        return copy;
    }
}
