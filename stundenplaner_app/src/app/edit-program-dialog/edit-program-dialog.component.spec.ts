import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditProgramDialogComponent } from './edit-program-dialog.component';

describe('EditProgramDialogComponent', () => {
  let component: EditProgramDialogComponent;
  let fixture: ComponentFixture<EditProgramDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditProgramDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditProgramDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
