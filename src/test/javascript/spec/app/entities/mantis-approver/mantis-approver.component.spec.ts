/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MantozorTestModule } from '../../../test.module';
import { MantisApproverComponent } from '../../../../../../main/webapp/app/entities/mantis-approver/mantis-approver.component';
import { MantisApproverService } from '../../../../../../main/webapp/app/entities/mantis-approver/mantis-approver.service';
import { MantisApprover } from '../../../../../../main/webapp/app/entities/mantis-approver/mantis-approver.model';

describe('Component Tests', () => {

    describe('MantisApprover Management Component', () => {
        let comp: MantisApproverComponent;
        let fixture: ComponentFixture<MantisApproverComponent>;
        let service: MantisApproverService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisApproverComponent],
                providers: [
                    MantisApproverService
                ]
            })
            .overrideTemplate(MantisApproverComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisApproverComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisApproverService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MantisApprover(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.mantisApprovers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
