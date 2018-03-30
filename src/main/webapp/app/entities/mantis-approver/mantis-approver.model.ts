import { BaseEntity } from './../../shared';

export class MantisApprover implements BaseEntity {
    constructor(
        public id?: number,
        public fullName?: string,
    ) {
    }
}
