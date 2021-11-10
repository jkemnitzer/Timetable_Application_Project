/**
 * Author(s): Jan Gaida
 * Package(s): #6077
 */

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LecturesOverviewComponent } from './lectures-overview.component';

describe('LecturesComponent', () => {
  let component: LecturesOverviewComponent;
  let fixture: ComponentFixture<LecturesOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LecturesOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LecturesOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
