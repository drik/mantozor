/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MantozorTestModule } from '../../../test.module';
import { MantisImportLineDialogComponent } from '../../../../../../main/webapp/app/entities/mantis-import-line/mantis-import-line-dialog.component';
import { MantisImportLineService } from '../../../../../../main/webapp/app/entities/mantis-import-line/mantis-import-line.service';
import { MantisImportLine } from '../../../../../../main/webapp/app/entities/mantis-import-line/mantis-import-line.model';
import { StateService } from '../../../../../../main/webapp/app/entities/state';
import { MantisImportService } from '../../../../../../main/webapp/app/entities/mantis-import';
import { MantisService } from '../../../../../../main/webapp/app/entities/mantis';

describe('Component Tests', () => {

    describe('MantisImportLine Management Dialog Component', () => {
        let comp: MantisImportLineDialogComponent;
        let fixture: ComponentFixture<MantisImportLineDialogComponent>;
        let service: MantisImportLineService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisImportLineDialogComponent],
                providers: [
                    StateService,
                    MantisImportService,
                    MantisService,
                    MantisImportLineService
                ]
            })
            .overrideTemplate(MantisImportLineDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisImportLineDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisImportLineService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MantisImportLine(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.mantisImportLine = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mantisImportLineListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MantisImportLine();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.mantisImportLine = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mantisImportLineListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
