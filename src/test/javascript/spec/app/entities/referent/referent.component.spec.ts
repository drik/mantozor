/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MantozorTestModule } from '../../../test.module';
import { ReferentComponent } from '../../../../../../main/webapp/app/entities/referent/referent.component';
import { ReferentService } from '../../../../../../main/webapp/app/entities/referent/referent.service';
import { Referent } from '../../../../../../main/webapp/app/entities/referent/referent.model';

describe('Component Tests', () => {

    describe('Referent Management Component', () => {
        let comp: ReferentComponent;
        let fixture: ComponentFixture<ReferentComponent>;
        let service: ReferentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [ReferentComponent],
                providers: [
                    ReferentService
                ]
            })
            .overrideTemplate(ReferentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReferentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReferentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Referent(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.referents[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
