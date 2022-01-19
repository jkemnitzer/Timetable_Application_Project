import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EditLessonFormComponent} from './edit-lesson-form.component';

describe('EditLessonFormComponent', () => {
  let component: EditLessonFormComponent;
  let fixture: ComponentFixture<EditLessonFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditLessonFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditLessonFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
