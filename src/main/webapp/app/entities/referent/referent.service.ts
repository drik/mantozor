import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Referent } from './referent.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Referent>;

@Injectable()
export class ReferentService {

    private resourceUrl =  SERVER_API_URL + 'api/referents';

    constructor(private http: HttpClient) { }

    create(referent: Referent): Observable<EntityResponseType> {
        const copy = this.convert(referent);
        return this.http.post<Referent>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(referent: Referent): Observable<EntityResponseType> {
        const copy = this.convert(referent);
        return this.http.put<Referent>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Referent>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Referent[]>> {
        const options = createRequestOption(req);
        return this.http.get<Referent[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Referent[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Referent = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Referent[]>): HttpResponse<Referent[]> {
        const jsonResponse: Referent[] = res.body;
        const body: Referent[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Referent.
     */
    private convertItemFromServer(referent: Referent): Referent {
        const copy: Referent = Object.assign({}, referent);
        return copy;
    }

    /**
     * Convert a Referent to a JSON which can be sent to the server.
     */
    private convert(referent: Referent): Referent {
        const copy: Referent = Object.assign({}, referent);
        return copy;
    }
}
