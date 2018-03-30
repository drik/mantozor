import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MantisImportLine } from './mantis-import-line.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MantisImportLine>;

@Injectable()
export class MantisImportLineService {

    private resourceUrl =  SERVER_API_URL + 'api/mantis-import-lines';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(mantisImportLine: MantisImportLine): Observable<EntityResponseType> {
        const copy = this.convert(mantisImportLine);
        return this.http.post<MantisImportLine>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(mantisImportLine: MantisImportLine): Observable<EntityResponseType> {
        const copy = this.convert(mantisImportLine);
        return this.http.put<MantisImportLine>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MantisImportLine>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MantisImportLine[]>> {
        const options = createRequestOption(req);
        return this.http.get<MantisImportLine[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MantisImportLine[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MantisImportLine = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MantisImportLine[]>): HttpResponse<MantisImportLine[]> {
        const jsonResponse: MantisImportLine[] = res.body;
        const body: MantisImportLine[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MantisImportLine.
     */
    private convertItemFromServer(mantisImportLine: MantisImportLine): MantisImportLine {
        const copy: MantisImportLine = Object.assign({}, mantisImportLine);
        copy.updateDate = this.dateUtils
            .convertLocalDateFromServer(mantisImportLine.updateDate);
        copy.submissionDate = this.dateUtils
            .convertLocalDateFromServer(mantisImportLine.submissionDate);
        copy.desiredCommitmentDate = this.dateUtils
            .convertLocalDateFromServer(mantisImportLine.desiredCommitmentDate);
        copy.commitmentDateCDS = this.dateUtils
            .convertLocalDateFromServer(mantisImportLine.commitmentDateCDS);
        copy.estimatedDSTDelivreryDate = this.dateUtils
            .convertLocalDateFromServer(mantisImportLine.estimatedDSTDelivreryDate);
        copy.recipeDate = this.dateUtils
            .convertLocalDateFromServer(mantisImportLine.recipeDate);
        copy.productionDate = this.dateUtils
            .convertLocalDateFromServer(mantisImportLine.productionDate);
        return copy;
    }

    /**
     * Convert a MantisImportLine to a JSON which can be sent to the server.
     */
    private convert(mantisImportLine: MantisImportLine): MantisImportLine {
        const copy: MantisImportLine = Object.assign({}, mantisImportLine);
        copy.updateDate = this.dateUtils
            .convertLocalDateToServer(mantisImportLine.updateDate);
        copy.submissionDate = this.dateUtils
            .convertLocalDateToServer(mantisImportLine.submissionDate);
        copy.desiredCommitmentDate = this.dateUtils
            .convertLocalDateToServer(mantisImportLine.desiredCommitmentDate);
        copy.commitmentDateCDS = this.dateUtils
            .convertLocalDateToServer(mantisImportLine.commitmentDateCDS);
        copy.estimatedDSTDelivreryDate = this.dateUtils
            .convertLocalDateToServer(mantisImportLine.estimatedDSTDelivreryDate);
        copy.recipeDate = this.dateUtils
            .convertLocalDateToServer(mantisImportLine.recipeDate);
        copy.productionDate = this.dateUtils
            .convertLocalDateToServer(mantisImportLine.productionDate);
        return copy;
    }
}
