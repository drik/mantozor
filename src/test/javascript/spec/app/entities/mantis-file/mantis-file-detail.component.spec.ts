/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MantozorTestModule } from '../../../test.module';
import { MantisFileDetailComponent } from '../../../../../../main/webapp/app/entities/mantis-file/mantis-file-detail.component';
import { MantisFileService } from '../../../../../../main/webapp/app/entities/mantis-file/mantis-file.service';
import { MantisFile } from '../../../../../../main/webapp/app/entities/mantis-file/mantis-file.model';

describe('Component Tests', () => {

    describe('MantisFile Management Detail Component', () => {
        let comp: MantisFileDetailComponent;
        let fixture: ComponentFixture<MantisFileDetailComponent>;
        let service: MantisFileService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisFileDetailComponent],
                providers: [
                    MantisFileService
                ]
            })
            .overrideTemplate(MantisFileDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisFileDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisFileService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MantisFile(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.mantisFile).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
