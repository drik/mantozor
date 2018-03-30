import { BaseEntity } from './../../shared';

export class MantisImport implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public importDate?: any,
        public fileContentType?: string,
        public file?: any,
        public userId?: number,
    ) {
    }
}
