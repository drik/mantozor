import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { MantisApprover } from './mantis-approver.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MantisApprover>;

@Injectable()
export class MantisApproverService {

    private resourceUrl =  SERVER_API_URL + 'api/mantis-approvers';

    constructor(private http: HttpClient) { }

    create(mantisApprover: MantisApprover): Observable<EntityResponseType> {
        const copy = this.convert(mantisApprover);
        return this.http.post<MantisApprover>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(mantisApprover: MantisApprover): Observable<EntityResponseType> {
        const copy = this.convert(mantisApprover);
        return this.http.put<MantisApprover>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MantisApprover>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MantisApprover[]>> {
        const options = createRequestOption(req);
        return this.http.get<MantisApprover[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MantisApprover[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MantisApprover = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MantisApprover[]>): HttpResponse<MantisApprover[]> {
        const jsonResponse: MantisApprover[] = res.body;
        const body: MantisApprover[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MantisApprover.
     */
    private convertItemFromServer(mantisApprover: MantisApprover): MantisApprover {
        const copy: MantisApprover = Object.assign({}, mantisApprover);
        return copy;
    }

    /**
     * Convert a MantisApprover to a JSON which can be sent to the server.
     */
    private convert(mantisApprover: MantisApprover): MantisApprover {
        const copy: MantisApprover = Object.assign({}, mantisApprover);
        return copy;
    }
}
