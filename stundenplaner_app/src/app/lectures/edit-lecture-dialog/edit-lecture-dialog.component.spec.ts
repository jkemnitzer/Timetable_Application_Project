/**
 * Author(s): Jan Gaida
 * Package(s): #6077
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EditLectureDialogComponent } from './edit-lecture-dialog.component';

describe('EditLectureDialogComponent', () => {
  let component: EditLectureDialogComponent;
  let fixture: ComponentFixture<EditLectureDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditLectureDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditLectureDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
