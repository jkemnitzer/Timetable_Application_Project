import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuardViewComponent } from './guard-view.component';

describe('GuardViewComponent', () => {
  let component: GuardViewComponent;
  let fixture: ComponentFixture<GuardViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GuardViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GuardViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
