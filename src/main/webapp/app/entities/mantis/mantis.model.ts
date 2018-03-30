import { BaseEntity } from './../../shared';

export class Mantis implements BaseEntity {
    constructor(
        public id?: number,
        public mantisNumber?: string,
        public submissionDate?: any,
        public projectId?: number,
    ) {
    }
}
