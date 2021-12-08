import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditUsersDialogComponent } from './edit-users-dialog.component.ts';

describe('EditRoomDialogComponent', () => {
  let component: EditUsersDialogComponent;
  let fixture: ComponentFixture<EditUsersDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditUsersDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditUsersDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
