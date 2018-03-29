import { BaseEntity } from './../../shared';

export const enum State {
    'ACCEPTED',
    'ANALYSED',
    'CLOSED'
}

export class Mantis implements BaseEntity {
    constructor(
        public id?: number,
        public number?: string,
        public state?: State,
        public submissionDate?: any,
        public projectId?: number,
        public referentId?: number,
    ) {
    }
}
