/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MantozorTestModule } from '../../../test.module';
import { MantisStatusDetailComponent } from '../../../../../../main/webapp/app/entities/mantis-status/mantis-status-detail.component';
import { MantisStatusService } from '../../../../../../main/webapp/app/entities/mantis-status/mantis-status.service';
import { MantisStatus } from '../../../../../../main/webapp/app/entities/mantis-status/mantis-status.model';

describe('Component Tests', () => {

    describe('MantisStatus Management Detail Component', () => {
        let comp: MantisStatusDetailComponent;
        let fixture: ComponentFixture<MantisStatusDetailComponent>;
        let service: MantisStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisStatusDetailComponent],
                providers: [
                    MantisStatusService
                ]
            })
            .overrideTemplate(MantisStatusDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisStatusDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisStatusService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MantisStatus(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.mantisStatus).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
