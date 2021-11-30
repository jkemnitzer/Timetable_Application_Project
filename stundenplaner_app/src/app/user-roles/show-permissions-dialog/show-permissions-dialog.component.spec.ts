/**
 * Author(s): Jan Gaida
 * Package(s): #6077
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ShowPermissionsDialogComponent } from './show-permissions-dialog.component';

describe('ShowPermissionsDialogComponent', () => {
  let component: ShowPermissionsDialogComponent;
  let fixture: ComponentFixture<ShowPermissionsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowPermissionsDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowPermissionsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
