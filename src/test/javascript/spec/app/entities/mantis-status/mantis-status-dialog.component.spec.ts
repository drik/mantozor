/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MantozorTestModule } from '../../../test.module';
import { MantisStatusDialogComponent } from '../../../../../../main/webapp/app/entities/mantis-status/mantis-status-dialog.component';
import { MantisStatusService } from '../../../../../../main/webapp/app/entities/mantis-status/mantis-status.service';
import { MantisStatus } from '../../../../../../main/webapp/app/entities/mantis-status/mantis-status.model';
import { MantisService } from '../../../../../../main/webapp/app/entities/mantis';
import { StatusService } from '../../../../../../main/webapp/app/entities/status';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { MantisApproverService } from '../../../../../../main/webapp/app/entities/mantis-approver';

describe('Component Tests', () => {

    describe('MantisStatus Management Dialog Component', () => {
        let comp: MantisStatusDialogComponent;
        let fixture: ComponentFixture<MantisStatusDialogComponent>;
        let service: MantisStatusService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisStatusDialogComponent],
                providers: [
                    MantisService,
                    StatusService,
                    UserService,
                    MantisApproverService,
                    MantisStatusService
                ]
            })
            .overrideTemplate(MantisStatusDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisStatusDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisStatusService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MantisStatus(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.mantisStatus = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mantisStatusListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MantisStatus();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.mantisStatus = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mantisStatusListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
