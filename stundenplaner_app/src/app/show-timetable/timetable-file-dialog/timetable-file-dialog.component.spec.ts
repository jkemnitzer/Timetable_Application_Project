import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimetableFileDialogComponent } from './timetable-file-dialog.component';

describe('TimetableFileDialogComponent', () => {
  let component: TimetableFileDialogComponent;
  let fixture: ComponentFixture<TimetableFileDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TimetableFileDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TimetableFileDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
