/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MantozorTestModule } from '../../../test.module';
import { ReferentDetailComponent } from '../../../../../../main/webapp/app/entities/referent/referent-detail.component';
import { ReferentService } from '../../../../../../main/webapp/app/entities/referent/referent.service';
import { Referent } from '../../../../../../main/webapp/app/entities/referent/referent.model';

describe('Component Tests', () => {

    describe('Referent Management Detail Component', () => {
        let comp: ReferentDetailComponent;
        let fixture: ComponentFixture<ReferentDetailComponent>;
        let service: ReferentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [ReferentDetailComponent],
                providers: [
                    ReferentService
                ]
            })
            .overrideTemplate(ReferentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReferentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReferentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Referent(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.referent).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
