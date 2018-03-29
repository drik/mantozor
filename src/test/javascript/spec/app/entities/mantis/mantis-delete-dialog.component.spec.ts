/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MantozorTestModule } from '../../../test.module';
import { MantisDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/mantis/mantis-delete-dialog.component';
import { MantisService } from '../../../../../../main/webapp/app/entities/mantis/mantis.service';

describe('Component Tests', () => {

    describe('Mantis Management Delete Component', () => {
        let comp: MantisDeleteDialogComponent;
        let fixture: ComponentFixture<MantisDeleteDialogComponent>;
        let service: MantisService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MantozorTestModule],
                declarations: [MantisDeleteDialogComponent],
                providers: [
                    MantisService
                ]
            })
            .overrideTemplate(MantisDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MantisDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MantisService);
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
