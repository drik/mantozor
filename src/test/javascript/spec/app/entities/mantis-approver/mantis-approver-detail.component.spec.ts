/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MantozorTestModule } from '../../../test.module';
import { MantisApproverDetailComponent } from '../../../../../../main/webapp/app/entities/mantis-approver/mantis-approver-detail.component';
import { MantisApproverService } from '../../../../../../main/webapp/app/entities/mantis-approver/mantis-approver.service';
import { MantisApprover } from '../../../../../../main/webapp/app/entities/mantis-approver/mantis-approver.model';

describe('Component Tests', () => {

    describe('MantisApprover Management Detail Component', () => {
        let comp: MantisApproverDetailComponent;
        let fixture: ComponentFixture<MantisApproverDetailComponent>;
        let service: MantisApproverService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisApproverDetailComponent],
                providers: [
                    MantisApproverService
                ]
            })
            .overrideTemplate(MantisApproverDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisApproverDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisApproverService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MantisApprover(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.mantisApprover).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
