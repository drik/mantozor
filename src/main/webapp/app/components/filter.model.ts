export class FilterModel {
    constructor(
        public concernedField?: string,
        public comparator?: string,
        public criteriaValue?: string,
      ) {}
}