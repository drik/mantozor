import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MantisImport } from './mantis-import.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MantisImport>;

@Injectable()
export class MantisImportService {

    private resourceUrl =  SERVER_API_URL + 'api/mantis-imports';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(mantisImport: MantisImport): Observable<EntityResponseType> {
        const copy = this.convert(mantisImport);
        return this.http.post<MantisImport>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(mantisImport: MantisImport): Observable<EntityResponseType> {
        const copy = this.convert(mantisImport);
        return this.http.put<MantisImport>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MantisImport>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MantisImport[]>> {
        const options = createRequestOption(req);
        return this.http.get<MantisImport[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MantisImport[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MantisImport = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MantisImport[]>): HttpResponse<MantisImport[]> {
        const jsonResponse: MantisImport[] = res.body;
        const body: MantisImport[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MantisImport.
     */
    private convertItemFromServer(mantisImport: MantisImport): MantisImport {
        const copy: MantisImport = Object.assign({}, mantisImport);
        copy.importDate = this.dateUtils
            .convertLocalDateFromServer(mantisImport.importDate);
        return copy;
    }

    /**
     * Convert a MantisImport to a JSON which can be sent to the server.
     */
    private convert(mantisImport: MantisImport): MantisImport {
        const copy: MantisImport = Object.assign({}, mantisImport);
        copy.importDate = this.dateUtils
            .convertLocalDateToServer(mantisImport.importDate);
        return copy;
    }
}
