/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MantozorTestModule } from '../../../test.module';
import { MantisImportDetailComponent } from '../../../../../../main/webapp/app/entities/mantis-import/mantis-import-detail.component';
import { MantisImportService } from '../../../../../../main/webapp/app/entities/mantis-import/mantis-import.service';
import { MantisImport } from '../../../../../../main/webapp/app/entities/mantis-import/mantis-import.model';

describe('Component Tests', () => {

    describe('MantisImport Management Detail Component', () => {
        let comp: MantisImportDetailComponent;
        let fixture: ComponentFixture<MantisImportDetailComponent>;
        let service: MantisImportService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisImportDetailComponent],
                providers: [
                    MantisImportService
                ]
            })
            .overrideTemplate(MantisImportDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisImportDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisImportService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MantisImport(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.mantisImport).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
