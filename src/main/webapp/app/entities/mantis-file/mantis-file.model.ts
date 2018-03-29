import { BaseEntity } from './../../shared';

export class MantisFile implements BaseEntity {
    constructor(
        public id?: number,
        public file?: any,
    ) {
    }
}
