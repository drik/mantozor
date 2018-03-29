/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MantozorTestModule } from '../../../test.module';
import { MantisComponent } from '../../../../../../main/webapp/app/entities/mantis/mantis.component';
import { MantisService } from '../../../../../../main/webapp/app/entities/mantis/mantis.service';
import { Mantis } from '../../../../../../main/webapp/app/entities/mantis/mantis.model';

describe('Component Tests', () => {

    describe('Mantis Management Component', () => {
        let comp: MantisComponent;
        let fixture: ComponentFixture<MantisComponent>;
        let service: MantisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisComponent],
                providers: [
                    MantisService
                ]
            })
            .overrideTemplate(MantisComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Mantis(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.mantis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
