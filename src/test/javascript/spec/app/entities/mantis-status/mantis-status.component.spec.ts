/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MantozorTestModule } from '../../../test.module';
import { MantisStatusComponent } from '../../../../../../main/webapp/app/entities/mantis-status/mantis-status.component';
import { MantisStatusService } from '../../../../../../main/webapp/app/entities/mantis-status/mantis-status.service';
import { MantisStatus } from '../../../../../../main/webapp/app/entities/mantis-status/mantis-status.model';

describe('Component Tests', () => {

    describe('MantisStatus Management Component', () => {
        let comp: MantisStatusComponent;
        let fixture: ComponentFixture<MantisStatusComponent>;
        let service: MantisStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisStatusComponent],
                providers: [
                    MantisStatusService
                ]
            })
            .overrideTemplate(MantisStatusComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisStatusComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisStatusService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MantisStatus(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.mantisStatuses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
