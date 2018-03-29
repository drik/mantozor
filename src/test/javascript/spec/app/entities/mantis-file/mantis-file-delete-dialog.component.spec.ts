/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MantozorTestModule } from '../../../test.module';
import { MantisFileDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/mantis-file/mantis-file-delete-dialog.component';
import { MantisFileService } from '../../../../../../main/webapp/app/entities/mantis-file/mantis-file.service';

describe('Component Tests', () => {

    describe('MantisFile Management Delete Component', () => {
        let comp: MantisFileDeleteDialogComponent;
        let fixture: ComponentFixture<MantisFileDeleteDialogComponent>;
        let service: MantisFileService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisFileDeleteDialogComponent],
                providers: [
                    MantisFileService
                ]
            })
            .overrideTemplate(MantisFileDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisFileDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisFileService);
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
