/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MantozorTestModule } from '../../../test.module';
import { MantisDetailComponent } from '../../../../../../main/webapp/app/entities/mantis/mantis-detail.component';
import { MantisService } from '../../../../../../main/webapp/app/entities/mantis/mantis.service';
import { Mantis } from '../../../../../../main/webapp/app/entities/mantis/mantis.model';

describe('Component Tests', () => {

    describe('Mantis Management Detail Component', () => {
        let comp: MantisDetailComponent;
        let fixture: ComponentFixture<MantisDetailComponent>;
        let service: MantisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisDetailComponent],
                providers: [
                    MantisService
                ]
            })
            .overrideTemplate(MantisDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Mantis(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.mantis).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
