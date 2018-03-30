import { BaseEntity } from './../../shared';

export class State implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
