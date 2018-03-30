import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MantisStatus } from './mantis-status.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MantisStatus>;

@Injectable()
export class MantisStatusService {

    private resourceUrl =  SERVER_API_URL + 'api/mantis-statuses';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(mantisStatus: MantisStatus): Observable<EntityResponseType> {
        const copy = this.convert(mantisStatus);
        return this.http.post<MantisStatus>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(mantisStatus: MantisStatus): Observable<EntityResponseType> {
        const copy = this.convert(mantisStatus);
        return this.http.put<MantisStatus>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MantisStatus>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MantisStatus[]>> {
        const options = createRequestOption(req);
        return this.http.get<MantisStatus[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MantisStatus[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MantisStatus = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MantisStatus[]>): HttpResponse<MantisStatus[]> {
        const jsonResponse: MantisStatus[] = res.body;
        const body: MantisStatus[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MantisStatus.
     */
    private convertItemFromServer(mantisStatus: MantisStatus): MantisStatus {
        const copy: MantisStatus = Object.assign({}, mantisStatus);
        copy.changeDate = this.dateUtils
            .convertLocalDateFromServer(mantisStatus.changeDate);
        return copy;
    }

    /**
     * Convert a MantisStatus to a JSON which can be sent to the server.
     */
    private convert(mantisStatus: MantisStatus): MantisStatus {
        const copy: MantisStatus = Object.assign({}, mantisStatus);
        copy.changeDate = this.dateUtils
            .convertLocalDateToServer(mantisStatus.changeDate);
        return copy;
    }
}
