/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MantozorTestModule } from '../../../test.module';
import { MantisImportComponent } from '../../../../../../main/webapp/app/entities/mantis-import/mantis-import.component';
import { MantisImportService } from '../../../../../../main/webapp/app/entities/mantis-import/mantis-import.service';
import { MantisImport } from '../../../../../../main/webapp/app/entities/mantis-import/mantis-import.model';

describe('Component Tests', () => {

    describe('MantisImport Management Component', () => {
        let comp: MantisImportComponent;
        let fixture: ComponentFixture<MantisImportComponent>;
        let service: MantisImportService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisImportComponent],
                providers: [
                    MantisImportService
                ]
            })
            .overrideTemplate(MantisImportComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisImportComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisImportService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MantisImport(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.mantisImports[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
