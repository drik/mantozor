import { BaseEntity } from './../../shared';

export class MantisImportLine implements BaseEntity {
    constructor(
        public id?: number,
        public mantisNumber?: string,
        public validationStatus?: string,
        public project?: string,
        public updateDate?: any,
        public category?: string,
        public gravity?: string,
        public augeoReference?: string,
        public technicalReference?: string,
        public description?: string,
        public submissionDate?: any,
        public desiredCommitmentDate?: any,
        public estimatedChargeCACF?: number,
        public commitmentDateCDS?: any,
        public estimatedChargeCDS?: number,
        public estimatedDSTDelivreryDate?: any,
        public recipeDate?: any,
        public productionDate?: any,
        public devStandardsComplianceScore?: string,
        public devStandardsComplianceScoreComment?: string,
        public expressedNeedsComplianceScore?: string,
        public expressedNeedsComplianceScoreComment?: string,
        public overallDeadlineRespectScore?: string,
        public overallDeadlineRespectScoreComment?: string,
        public stateId?: number,
        public mantisImportId?: number,
        public mantisId?: number,
    ) {
    }
}
