import { BaseEntity } from './../../shared';

export const enum Status {
    'ACCEPTED',
    'ANALYSED',
    'CLOSED'
}

export class MantisStatus implements BaseEntity {
    constructor(
        public id?: number,
        public status?: Status,
        public changeDate?: any,
        public mantisId?: number,
    ) {
    }
}
