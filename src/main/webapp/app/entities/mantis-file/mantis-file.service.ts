import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { MantisFile } from './mantis-file.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MantisFile>;

@Injectable()
export class MantisFileService {

    private resourceUrl =  SERVER_API_URL + 'api/mantis-files';

    constructor(private http: HttpClient) { }

    create(mantisFile: MantisFile): Observable<EntityResponseType> {
        const copy = this.convert(mantisFile);
        return this.http.post<MantisFile>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(mantisFile: MantisFile): Observable<EntityResponseType> {
        const copy = this.convert(mantisFile);
        return this.http.put<MantisFile>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MantisFile>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MantisFile[]>> {
        const options = createRequestOption(req);
        return this.http.get<MantisFile[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MantisFile[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MantisFile = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MantisFile[]>): HttpResponse<MantisFile[]> {
        const jsonResponse: MantisFile[] = res.body;
        const body: MantisFile[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MantisFile.
     */
    private convertItemFromServer(mantisFile: MantisFile): MantisFile {
        const copy: MantisFile = Object.assign({}, mantisFile);
        return copy;
    }

    /**
     * Convert a MantisFile to a JSON which can be sent to the server.
     */
    private convert(mantisFile: MantisFile): MantisFile {
        const copy: MantisFile = Object.assign({}, mantisFile);
        return copy;
    }
}
