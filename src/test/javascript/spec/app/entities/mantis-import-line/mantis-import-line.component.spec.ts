/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MantozorTestModule } from '../../../test.module';
import { MantisImportLineComponent } from '../../../../../../main/webapp/app/entities/mantis-import-line/mantis-import-line.component';
import { MantisImportLineService } from '../../../../../../main/webapp/app/entities/mantis-import-line/mantis-import-line.service';
import { MantisImportLine } from '../../../../../../main/webapp/app/entities/mantis-import-line/mantis-import-line.model';

describe('Component Tests', () => {

    describe('MantisImportLine Management Component', () => {
        let comp: MantisImportLineComponent;
        let fixture: ComponentFixture<MantisImportLineComponent>;
        let service: MantisImportLineService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisImportLineComponent],
                providers: [
                    MantisImportLineService
                ]
            })
            .overrideTemplate(MantisImportLineComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisImportLineComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisImportLineService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MantisImportLine(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.mantisImportLines[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
