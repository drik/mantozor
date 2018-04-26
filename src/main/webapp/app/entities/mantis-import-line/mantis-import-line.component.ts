import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { FormArray, FormGroup, FormBuilder } from '@angular/forms';

import { MantisImportLine } from './mantis-import-line.model';
import { MantisImport, MantisImportService } from '../mantis-import';
import { Referent, ReferentService } from '../referent';
import { MantisImportLineService } from './mantis-import-line.service';
import { State, StateService } from '../state';
import { ITEMS_PER_PAGE, Principal } from '../../shared';

@Component({
    selector: 'jhi-mantis-import-line',
    templateUrl: './mantis-import-line.component.html'
})
export class MantisImportLineComponent implements OnInit, OnDestroy {

    currentAccount: any;
    mantisImportLines: MantisImportLine[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    criteria: any;

    referent: Referent;

    mantisImports: MantisImport[];

    rowModels: any[];
    filterForm: FormGroup;

    filterFields: any;
    filterComparators: any;

    filterBoxExpended: any = true;

    states: State[];

    statesSelected: any[];

    constructor(
        private mantisImportLineService: MantisImportLineService,
        private referentService: ReferentService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private formBuilder: FormBuilder,
        private mantisImportService: MantisImportService,
        private stateService: StateService,
    ) {

        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });

        this.filterFields = this.getFilterFields();
        this.filterComparators = this.getFilterComparators(null);

        this.rowModels = [{}];
        this.filterForm = this.formBuilder.group({
          filterMantisImport: [1],
          filterReferentMantisOnly: [true],
          filterRows: this.formBuilder.array([this.initFilterRows()])
        });
    }

    initFilterRows() {
      this.rowModels.push({comparators: []});
      return this.formBuilder.group({
          concernedField: {label: 'mantisNumber', value: 'mantisNumber', category: 'text'},
          comparator: {key: 'contains', value: 'contains', label: 'Contains'},
          criteriaValue: '',
      });
    }

    addCriteria() {
      this.filterBoxExpended = true;
      const control = <FormArray>this.filterForm.controls['filterRows'];
      control.push(this.initFilterRows());
    }

    removeCriteria(index) {
      const control = <FormArray>this.filterForm.controls['filterRows'];
      control.removeAt(index);
      this.rowModels.splice(index, 1);
    }

    fieldChanged(event, index) {
      this.rowModels[index].comparators = this.getFilterComparators(event);
    }

    comparatorChanged(event) {
    }

    loadAll() {
        const criteria = [];

        //Gestion critere importation
        if (this.filterForm.value.filterMantisImport !== '') {
          criteria.push({key: 'mantisImportId.equals', value: this.filterForm.value.filterMantisImport});
        }
        //Gestion mantis utilisateur
        console.log(this.referent);
        console.log(this.filterForm.value.filterReferentMantisOnly );
        if(this.referent !== null && this.filterForm.value.filterReferentMantisOnly === true){
           
        }
        //Gestion critere formulaire champs dynamiques
        this.filterForm.value.filterRows.forEach(function(value) {
          console.log(value);
          if (value.concenedField !== null && value.concernedField !== '' &&
              value.comparator !== null && value.comparator !== '' &&
              value.criteriaValue !== null && value.criteriaValue !== '') {
              if (value.comparator.key === 'in') {
                  criteria.push({key: value.concernedField.value + '.' + value.comparator.key, value: value.criteriaValue.split('|')});
              }else {
                  criteria.push({key: value.concernedField.value + '.' + value.comparator.key, value: value.criteriaValue});
              }
          }
        });

        this.mantisImportLineService.query({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort(),
            criteria}).subscribe(
                (res: HttpResponse<MantisImportLine[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }
    transition() {
        this.router.navigate(['/mantis-import-line'], {queryParams:
            {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/mantis-import-line', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.criteria.clear();
        this.loadAll();
    }

    ngOnInit() {
        this.mantisImportService.query().subscribe((res: HttpResponse<MantisImport[]>) => {
                this.mantisImports = res.body;
              },
              (res: HttpErrorResponse) => this.onError(res.message)
          );

        this.referentService.findForCurrentUser().subscribe((res: HttpResponse<Referent>) => {
                this.referent = res.body;
              },
              (res: HttpErrorResponse) => this.onError(res.message)
          );

      this.stateService.query()
            .subscribe((res: HttpResponse<State[]>) => { this.states = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));

        this.loadAll();

        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });

        this.registerChangeInMantisImportLines();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MantisImportLine) {
        return item.id;
    }
    registerChangeInMantisImportLines() {
        this.eventSubscriber = this.eventManager.subscribe('mantisImportLineListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    search(criteria) {
        if (criteria.areSet()) {
            this.loadAll();
        }
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.mantisImportLines = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    getFilterComparators(field: any) {
      let comparators = [];
      if (field !== null) {
        if (field.category === 'text') {
            comparators = [
            {key: 'contains', value: 'contains', label: 'Contains'},
              {key: 'equals', value: 'equals', label: 'Equals (=)'},
              {key: 'in', value: 'in', label: 'In'},
            ];
        }else if (field.category === 'number' || field.category === 'date') {
          comparators = [
              {key: 'equals', value: 'equals', label: 'Equals (=)'},
              {key: 'greaterThan', value: 'greaterThan', label: 'Greater than (>)'},
              {key: 'lessThan', value: 'lessThan', label: 'Less than (<)'},
              {key: 'greaterOrEqualThan', value: 'greaterOrEqualThan', label: 'Greater than or equals to (>=)'},
              {key: 'lessOrEqualThan', value: 'lessOrEqualThan', label: 'Less than or equals to (<=)'},
          ];
        }
      }

      return comparators;
    }

    getFilterFields() {
      const fields = [
          {label: 'mantisNumber', value: 'mantisNumber', category: 'text'},
          {label: 'validationStatus', value: 'validationStatus', category: 'text'},
          {label: 'project', value: 'project', category: 'text'},
          {label: 'updateDate', value: 'updateDate', category: 'date'},
          {label: 'category', value: 'category', category: 'text'},
          {label: 'gravity', value: 'gravity', category: 'text'},
          {label: 'augeoReference', value: 'augeoReference', category: 'text'},
          {label: 'technicalReference', value: 'technicalReference', category: 'text'},
          {label: 'description', value: 'description', category: 'text'},
          {label: 'submissionDate', value: 'submissionDate', category: 'date'},
          {label: 'desiredCommitmentDate', value: 'desiredCommitmentDate', category: 'date'},
          {label: 'estimatedChargeCACF', value: 'estimatedChargeCACF', category: 'number'},
          {label: 'commitmentDateCDS', value: 'commitmentDateCDS', category: 'date'},
          {label: 'estimatedChargeCDS', value: 'estimatedChargeCDS', category: 'number'},
          {label: 'estimatedDSTDelivreryDate', value: 'estimatedDSTDelivreryDate', category: 'date'},
          {label: 'recipeDate', value: 'recipeDate', category: 'date'},
          {label: 'productionDate', value: 'productionDate', category: 'date'},
          {label: 'estimatedChargeCDS', value: 'estimatedChargeCDS', category: 'number'},
          {label: 'devStandardsComplianceScore', value: 'devStandardsComplianceScore', category: 'text'},
          {label: 'devStandardsComplianceScoreComment', value: 'devStandardsComplianceScore', category: 'text'},
          {label: 'expressedNeedsComplianceScore', value: 'expressedNeedsComplianceScore', category: 'text'},
          {label: 'expressedNeedsComplianceScoreComment', value: 'expressedNeedsComplianceScoreComment', category: 'text'},
          {label: 'overallDeadlineRespectScore', value: 'overallDeadlineRespectScore', category: 'text'},
          {label: 'overallDeadlineRespectScoreComment', value: 'overallDeadlineRespectScoreComment', category: 'text'},
      ];
      return fields;
    }

    getStatusClass(color: string){
      if (color === 'red') {
        return 'label label-danger';
      } else if (color === 'yellow') {
        return 'label label-warning';
      } else if (color === 'green') {
        return 'label label-success';
      } else if (color === 'blue') {
        return 'label label-info';
      } else if (color === 'orange') {
        return 'label bg-orange';
      } else if (color === 'olive') {
        return 'label bg-olive';
      }
    }
  
  onSelect2(event:any){
    
  }
}
