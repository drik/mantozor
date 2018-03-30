import { BaseEntity } from './../../shared';

export class MantisStatus implements BaseEntity {
    constructor(
        public id?: number,
        public changeDate?: any,
        public mantisId?: number,
        public statusId?: number,
        public userId?: number,
        public approverId?: number,
    ) {
    }
}
