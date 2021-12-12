import {Component, OnInit} from '@angular/core';
import {HttpService} from "../http/http.service";
import {FormControl, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";

export enum LessonType {
  LECTURE, EXERCISE
}

export class Lecturer {
  email: String = '';
  firstName: String = '';
  id: Number = 0;
  lastName: String = '';
  roles: any[] = [];
  status: String = '';
  title: String = '';
  username: String = '';
  displayName: String = '';
}

export class Lesson {
  endTime: Date | String = new Date();
  startTime: Date | String = new Date();
  id: Number = 0;
  lectureId: Number = 0;
  lectureTitle: String = '';
  lecturer: String = '';
  lecturerId: Number = 0;
  lessonType: String = '';
  note: String = '';
  room: String = '';
  roomId: Number = 0;
  timeslotId: Number = 0;
  versionId: Number = 0;
  weekdayNr: Number = 0;
  error : String = '';
}


@Component({
  selector: 'app-show-timetable',
  templateUrl: './show-timetable.component.html',
  styleUrls: ['./show-timetable.component.css']
})

export class ShowTimetableComponent implements OnInit {

  mondayData: Lesson[] = [];
  tuesdayData: Lesson[] = [];
  wednesdayData: Lesson[] = [];
  thursdayData: Lesson[] = [];
  fridayData: Lesson[] = [];
  saturdayData: Lesson[] = [];

  BASE_TIMETABLE_URL: string = '/timetable';
  BASE_ADD_LESSON_URL: string = '/timetable/lesson';
  BASE_LECTURERS_URL: string = '/users';
  filter: string = '';

  faculties = [
    {name: 'Informatik'},
    {name: 'Ingenieurwissenschaften'},
    {name: 'Interdisziplinäre und innovative Wissenschaften'},
    {name: 'Wirtschaftswissenschaften'},
  ];
  selectedFaculty = this.faculties[1];
  facultyFormController = new FormControl('', [Validators.required]);

  majors = [
    {name: 'Allgemeine Informatik'},
    {name: 'Medien Informatik'},
    {name: 'Maschinenbau'},
  ];
  selectedMajor = this.majors[1];
  majorsFormControl = new FormControl('', [Validators.required]);

  semesters = [
    {semester: '1'},
    {semester: '2'},
    {semester: '3'},
  ];
  selectedSemester = this.semesters[1];
  semesterFormControl = new FormControl('', [Validators.required]);

  lectures = [];
  lecturesFormControl = new FormControl('', [Validators.required]);
  days = [
    {name: 'Montag', id: 0},
    {name: 'Dienstag', id: 1},
    {name: 'Mittwoch', id: 2},
    {name: 'Donnerstag', id: 3},
    {name: 'Freitag', id: 4},
    {name: 'Samstag', id: 5},
  ];
  isCreateLessonVisible: boolean = false;
  daysFormControl = new FormControl('', [Validators.required]);
  startTimeFormControl = new FormControl('', [Validators.required]);
  endTimeFormControl = new FormControl('', [Validators.required]);

  lecturers: Lecturer[] = [];
  filteredLecturers: Observable<Lecturer[]> = new Observable<Lecturer[]>();
  lecturerFormControl = new FormControl('', [Validators.required]);
  roomFormControl =  new FormControl('', [Validators.required]);

  lessonTypes = [
    {enum: 'LECTURE', name: 'Vorlesung'},
    {enum: 'EXERCISE', name: 'Übung'},
  ];
  typeFormControl =  new FormControl('', [Validators.required]);


  constructor(private httpService: HttpService,) {
    this.getTimetable();
  }

  ngOnInit(): void {
    this.filteredLecturers = this.lecturerFormControl.valueChanges.pipe(
      startWith(''),
      map(name => (name ? this.filterLecturer(name) : this.lecturers.slice())))
  }
  private filterLecturer(value: string): Lecturer[] {
    if(!value)return this.lecturers;
    const filterValue = value.toLowerCase();

    return this.lecturers.filter(lecturer => {
      lecturer.displayName='';
      if (!!lecturer.title){
        lecturer.displayName =lecturer.title;
      }
      if (!!lecturer.firstName){
        lecturer.displayName = lecturer.displayName +' '+ lecturer.firstName;
      }
      if (!!lecturer.lastName){
        lecturer.displayName = lecturer.displayName +' '+ lecturer.lastName;
      }
      if (!lecturer.displayName) {
        return;
      }
      console.log(lecturer.displayName)
      return lecturer.displayName.toLowerCase().includes(filterValue);
    });
  }


  applyFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.filter = filterValue.trim().toLowerCase();
  }

  addLesson() {
    if(this.containsErrors()){
      this.touchAllInputs();
      return;
    }
    const lesson = new Lesson();
    lesson.lectureId = this.lecturesFormControl.value
    lesson.weekdayNr = this.daysFormControl.value.id;
    lesson.startTime = this.startTimeFormControl.value;
    lesson.endTime = this.startTimeFormControl.value;
    lesson.lecturer = this.lecturerFormControl.value;
    lesson.roomId = this.roomFormControl.value;
    lesson.lessonType = this.typeFormControl.value;
    console.log(lesson);
    const updatedLesson = this.createNewLesson(lesson);
    if(updatedLesson == null){
      console.log('Lesson could not be created');
      return;
    }
    if (updatedLesson.weekdayNr == 0) this.mondayData.push(updatedLesson);
    else if (updatedLesson.weekdayNr == 1) this.tuesdayData.push(updatedLesson);
    else if (updatedLesson.weekdayNr == 2) this.wednesdayData.push(updatedLesson);
    else if (updatedLesson.weekdayNr == 3) this.thursdayData.push(updatedLesson);
    else if (updatedLesson.weekdayNr == 4) this.fridayData.push(updatedLesson);
    else if (updatedLesson.weekdayNr == 5) this.saturdayData.push(updatedLesson);
  }

  getTimetable() {
    this.httpService.getRequest(this.BASE_TIMETABLE_URL).subscribe(
      (response) => {
        const mondayTemp: Lesson[] = [];
        const tuesdayTemp: Lesson[] = [];
        const wednesdayTemp: Lesson[] = [];
        const thursdayTemp: Lesson[] = [];
        const fridayTemp: Lesson[] = [];
        const saturdayTemp: Lesson[] = [];
        response.forEach((lesson: Lesson) => {
          lesson.startTime = new Date('December 17, 1995 ' + lesson.startTime.toString());
          lesson.endTime = new Date('December 17, 1995 ' + lesson.endTime.toString());
          //lesson.error = 'warning';
          //lesson.error = 'Oh Nooooooooooooooooooooooooooooooooo';

          if (lesson.weekdayNr == 0) mondayTemp.push(lesson);
          else if (lesson.weekdayNr == 1) tuesdayTemp.push(lesson);
          else if (lesson.weekdayNr == 2) wednesdayTemp.push(lesson);
          else if (lesson.weekdayNr == 3) thursdayTemp.push(lesson);
          else if (lesson.weekdayNr == 4) fridayTemp.push(lesson);
          else if (lesson.weekdayNr == 5) saturdayTemp.push(lesson);
        })
        this.mondayData = mondayTemp;
        this.tuesdayData = tuesdayTemp;
        this.wednesdayData = wednesdayTemp;
        this.thursdayData = thursdayTemp;
        this.fridayData = fridayTemp;
        this.saturdayData = saturdayTemp;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  private createNewLesson(lesson: Lesson): Lesson | null {
    let returnedLesson = null;
    this.httpService.postRequest(this.BASE_ADD_LESSON_URL, lesson).subscribe(
      (response) => {
        returnedLesson = response;
      },
      (error) => {
        console.error(error);
      }
    );
    return returnedLesson;
  }

  openCreateLesson() {
    this.loadLecturers();
    this.isCreateLessonVisible = true;
  }

  loadLecturers() {
    this.httpService.getRequest(this.BASE_LECTURERS_URL).subscribe(
      (response) => {
        this.lecturers = response;
        console.log(this.lecturers);
        console.log(this.filteredLecturers);
      },
      (error) => {
        console.error(error);
      }
    );
    console.log(this.filteredLecturers);
  }

  closeCreateLesson() {
    this.isCreateLessonVisible = false;
  }

  deleteNewLessonInputs() {
    this.lecturesFormControl.reset();
    this.daysFormControl.reset();
    this.startTimeFormControl.reset();
    this.endTimeFormControl.reset();
    this.lecturerFormControl.reset();
    this.roomFormControl.reset();
    this.typeFormControl.reset();
  }

  private containsErrors() {
    return !!this.facultyFormController.errors ||
      !!this.majorsFormControl.errors ||
      !!this.lecturesFormControl.errors ||
      !!this.daysFormControl.errors ||
      !!this.startTimeFormControl.errors ||
      !!this.endTimeFormControl.errors ||
      !!this.roomFormControl.errors ||
      !!this.lecturerFormControl.errors ||
      !!this.typeFormControl.errors;
  }

  private touchAllInputs() {
    this.facultyFormController.markAsTouched();
    this.majorsFormControl.markAsTouched();
    this.semesterFormControl.markAsTouched();
    this.lecturesFormControl.markAsTouched();
    this.daysFormControl.markAsTouched();
    this.startTimeFormControl.markAsTouched();
    this.endTimeFormControl.markAsTouched();
    this.lecturerFormControl.markAsTouched();
    this.roomFormControl.markAsTouched();
    this.typeFormControl.markAsTouched();
  }
}
