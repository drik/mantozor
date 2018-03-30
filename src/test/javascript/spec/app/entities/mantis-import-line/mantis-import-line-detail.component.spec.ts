/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MantozorTestModule } from '../../../test.module';
import { MantisImportLineDetailComponent } from '../../../../../../main/webapp/app/entities/mantis-import-line/mantis-import-line-detail.component';
import { MantisImportLineService } from '../../../../../../main/webapp/app/entities/mantis-import-line/mantis-import-line.service';
import { MantisImportLine } from '../../../../../../main/webapp/app/entities/mantis-import-line/mantis-import-line.model';

describe('Component Tests', () => {

    describe('MantisImportLine Management Detail Component', () => {
        let comp: MantisImportLineDetailComponent;
        let fixture: ComponentFixture<MantisImportLineDetailComponent>;
        let service: MantisImportLineService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisImportLineDetailComponent],
                providers: [
                    MantisImportLineService
                ]
            })
            .overrideTemplate(MantisImportLineDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisImportLineDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisImportLineService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MantisImportLine(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.mantisImportLine).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
