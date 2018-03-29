/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MantozorTestModule } from '../../../test.module';
import { MantisFileComponent } from '../../../../../../main/webapp/app/entities/mantis-file/mantis-file.component';
import { MantisFileService } from '../../../../../../main/webapp/app/entities/mantis-file/mantis-file.service';
import { MantisFile } from '../../../../../../main/webapp/app/entities/mantis-file/mantis-file.model';

describe('Component Tests', () => {

    describe('MantisFile Management Component', () => {
        let comp: MantisFileComponent;
        let fixture: ComponentFixture<MantisFileComponent>;
        let service: MantisFileService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisFileComponent],
                providers: [
                    MantisFileService
                ]
            })
            .overrideTemplate(MantisFileComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisFileComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisFileService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MantisFile(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.mantisFiles[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
