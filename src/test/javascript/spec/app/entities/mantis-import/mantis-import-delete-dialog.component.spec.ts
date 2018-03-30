/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MantozorTestModule } from '../../../test.module';
import { MantisImportDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/mantis-import/mantis-import-delete-dialog.component';
import { MantisImportService } from '../../../../../../main/webapp/app/entities/mantis-import/mantis-import.service';

describe('Component Tests', () => {

    describe('MantisImport Management Delete Component', () => {
        let comp: MantisImportDeleteDialogComponent;
        let fixture: ComponentFixture<MantisImportDeleteDialogComponent>;
        let service: MantisImportService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisImportDeleteDialogComponent],
                providers: [
                    MantisImportService
                ]
            })
            .overrideTemplate(MantisImportDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisImportDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisImportService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
