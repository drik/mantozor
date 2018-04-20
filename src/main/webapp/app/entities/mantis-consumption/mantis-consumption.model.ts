import { BaseEntity } from './../../shared';

export class MantisConsumption implements BaseEntity {
    constructor(
        public id?: number,
        public submissionDate?: any,
        public consumed?: number,
        public toBill?: number,
        public userId?: number,
        public mantisId?: number,
        public mantis?: any,
    ) {
    }
}
