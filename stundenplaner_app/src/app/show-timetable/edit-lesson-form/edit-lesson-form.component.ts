import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {Day, Lecture, Lecturer, Lesson, Room, TimeSlot} from "../show-timetable.component";
import {Observable} from "rxjs";
import {HttpService} from "../../http/http.service";
import {map, startWith} from "rxjs/operators";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ErrorSnackbarComponent} from "../error-snackbar/error-snackbar.component";
import {SuccessSnackbarComponent} from "../success-snackbar/success-snackbar.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Version} from "../data/version";

export interface DialogData {
  lesson: Lesson;
  version: Version;
}

@Component({
  selector: 'app-edit-lesson-form',
  templateUrl: './edit-lesson-form.component.html',
  styleUrls: ['./edit-lesson-form.component.css']
})
export class EditLessonFormComponent implements OnInit {

  BASE_EDIT_LESSON_URL: string = '/timetable/lesson/';
  BASE_LECTURERS_URL: string = '/users';
  BASE_ROOMS_URL: string = '/rooms';
  BASE_LECTURES_URL: string = '/lectures';
  BASE_TIMESLOTS_URL: string = '/timeSlots';



  lecturesFormControl = new FormControl('', [Validators.required]);
  days: Day[] = [
    {name: 'Montag', id: 1},
    {name: 'Dienstag', id: 2},
    {name: 'Mittwoch', id: 3},
    {name: 'Donnerstag', id: 4},
    {name: 'Freitag', id: 5},
    {name: 'Samstag', id: 6},
  ];
  daysFormControl = new FormControl('', [Validators.required]);
  timeSlotsFormControl = new FormControl('', [Validators.required]);

  lecturers: Lecturer[] = [];
  rooms: Room[] = [];
  lectures: Lecture[] = [];
  filteredLecturers: Observable<Lecturer[]> = new Observable<Lecturer[]>();
  filteredRooms: Observable<Room[]> = new Observable<Room[]>();
  filteredLectures: Observable<Lecture[]> = new Observable<Lecture[]>();
  lecturerFormControl = new FormControl('', [Validators.required]);
  roomFormControl =  new FormControl('', [Validators.required]);
  lessonTypes = [
    {enum: 'LECTURE', name: 'Vorlesung'},
    {enum: 'EXERCISE', name: 'Übung'},
  ];
  typeFormControl =  new FormControl('', [Validators.required]);
  timeSlots: TimeSlot[] = [];
  filteredTimeSlots: TimeSlot[] = [];
  lessonNameFormControl = new FormControl();
  returnedLesson: Lesson|null = null;

  constructor(private httpService: HttpService, @Inject(MAT_DIALOG_DATA) public data: DialogData, public dialogRef: MatDialogRef<EditLessonFormComponent>,private _snackBar: MatSnackBar) {
  }

  private loadLecturers() {
    this.httpService.getRequest(this.BASE_LECTURERS_URL).subscribe(
      (response) => {
        this.lecturers = response;
        const lecturer = new Lecturer();
        lecturer.displayName = this.data.lesson.lecturer;
        lecturer.id = this.data.lesson.lecturerId;
        this.lecturerFormControl.setValue(lecturer);
      },
      (error) => {
        console.error(error);
      }
    );
  }
  private loadRooms() {
    this.httpService.getRequest(this.BASE_ROOMS_URL).subscribe(
      (response) => {
        this.rooms = response;
        const room = new Room();
        room.number = this.data.lesson.room;
        room.id = this.data.lesson.roomId;
        this.roomFormControl.setValue(room);
      },
      (error) => {
        console.error(error);
      }
    );
  }
  private loadLectures() {
    this.httpService.getRequest(this.BASE_LECTURES_URL).subscribe(
      (response) => {
        this.lectures = response;
        const lecture = new Lecture();
        lecture.lectureName = this.data.lesson.lectureTitle;
        lecture.id = this.data.lesson.lectureId;
        this.lecturesFormControl.setValue(lecture);
      },
      (error) => {
        console.error(error);
      }
    );
  }
  private loadTimeslots() {
    this.httpService.getRequest(this.BASE_TIMESLOTS_URL).subscribe(
      (response) => {
        this.timeSlots = response;

        this.days.forEach(value => {
          if(value.id == this.data.lesson.weekdayNr) this.daysFormControl.setValue(value);
        });

        this.filterTimeSlots();
        this.timeSlots.forEach(value => {
          if(value.id == this.data.lesson.timeslotId) this.timeSlotsFormControl.setValue(value);
        });


      },
      (error) => {
        console.error(error);
      }
    );
  }

  deleteNewLessonInputs() {
    this.lecturesFormControl.reset();
    this.daysFormControl.reset();
    this.timeSlotsFormControl.reset();
    this.lecturerFormControl.reset();
    this.roomFormControl.reset();
    this.typeFormControl.reset();
    this.lessonNameFormControl.reset();
  }

  private containsErrors() {
    return !!this.lecturesFormControl.errors ||
      !!this.daysFormControl.errors ||
      !!this.roomFormControl.errors ||
      !!this.lecturerFormControl.errors ||
      !!this.typeFormControl.errors||
    !!this.timeSlotsFormControl.errors;
  }
  showError() {
    this._snackBar.openFromComponent(ErrorSnackbarComponent, {
      duration:5000,
    });
  }
  private showSuccess(message: string) {
    this._snackBar.openFromComponent(SuccessSnackbarComponent, {
      duration:5000,
      data: message
    },);
  }
  private touchAllInputs() {
    this.lecturesFormControl.markAsTouched();
    this.daysFormControl.markAsTouched();
    this.lecturerFormControl.markAsTouched();
    this.roomFormControl.markAsTouched();
    this.typeFormControl.markAsTouched();
    this.lessonNameFormControl.markAsTouched();
    this.timeSlotsFormControl.markAsTouched();
  }
  getLecturerName(lecturer: Lecturer) {
    if(!lecturer) return '';
    return lecturer.displayName.toString();
  }
  getLectureName(lecturer: Lecture) {
    if(!lecturer) return '';
    return lecturer.lectureName.toString();
  }
  getRoomName(lecturer: Room) {
    if(!lecturer) return '';
    return lecturer.number.toString();
  }
  editLesson() {
    if(this.containsErrors()){
      this.touchAllInputs();
      return;
    }
    const lesson = new Lesson();
    lesson.id = this.data.lesson.id;
    lesson.lectureTitle = this.lecturesFormControl.value.lectureName;
    lesson.lectureId = this.lecturesFormControl.value.id;
    lesson.note = this.lessonNameFormControl.value;
    lesson.weekdayNr = this.daysFormControl.value.id;
    lesson.timeslotId = this.timeSlotsFormControl.value.id;
    lesson.startTime = this.timeSlotsFormControl.value.start;
    lesson.endTime = this.timeSlotsFormControl.value.end;
    lesson.lecturer = this.lecturerFormControl.value.displayName;
    lesson.lecturerId = this.lecturerFormControl.value.id;
    lesson.room = this.roomFormControl.value.number;
    lesson.roomId = this.roomFormControl.value.id;
    lesson.lessonType = this.typeFormControl.value.enum;
    lesson.versionId = this.data.version.id;

    return this.updateLesson(lesson);
  }
  private updateLesson(lesson: Lesson): Lesson | null {
    let returnedLesson:Lesson = new Lesson();
    this.httpService.updateRequest(this.BASE_EDIT_LESSON_URL + lesson.id, lesson).subscribe(
      (response) => {
        returnedLesson = response;
        returnedLesson.startTime = new Date('December 17, 1995 ' + returnedLesson.startTime.toString());
        returnedLesson.endTime = new Date('December 17, 1995 ' + returnedLesson.endTime.toString());
        this.returnedLesson = returnedLesson;
        this.dialogRef.close(returnedLesson);
        this.showSuccess('edited');
      },
      () => {
        this.showError()
      }
    );


    return returnedLesson;
  }

  ngOnInit(): void {
    this.loadLecturers();
    this.loadRooms();
    this.loadLectures();
    this.loadTimeslots();
    this.lessonTypes.forEach(value => {
      if(value.enum == this.data.lesson.lessonType)this.typeFormControl.setValue(value);
    });


    this.filteredRooms = this.roomFormControl.valueChanges.pipe(
      startWith(''),
      map((input: string) => (input ? this.filterRooms(input) : this.rooms.slice())));
    this.filteredLecturers = this.lecturerFormControl.valueChanges.pipe(
      startWith(''),
      map(name => (name ? this.filterLecturer(name) : this.lecturers.slice())));
    this.filteredLectures = this.lecturesFormControl.valueChanges.pipe(
      startWith(''),
      map((input: string) => (input ? this.filterLectures(input) : this.lectures.slice())));

  }
  private filterLecturer(value: any): Lecturer[] {
    if(!value)return this.lecturers;
    if(!(typeof value == 'string'))return this.lecturers;
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
      return lecturer.displayName.toLowerCase().includes(filterValue);
    });
  }
  private filterRooms(value: any): Room[] {
    if(!value)return this.rooms;
    if(!(typeof value == 'string'))return this.rooms;
    const filterValue = value.toLowerCase();

    return this.rooms.filter( room=> {
      if (!room.number) {
        return;
      }
      return room.number.toLowerCase().includes(filterValue);
    });
  }
  private filterLectures(value: any): Lecture[] {
    if(!value)return this.lectures;
    if(!(typeof value == 'string'))return this.lectures;
    const filterValue = value.toLowerCase();

    return this.lectures.filter( lecture=> {
      if (!lecture.lectureName) {
        return;
      }
      return lecture.lectureName.toLowerCase().includes(filterValue);
    });
  }
  filterTimeSlots(){
    const day:Day = this.daysFormControl.value;
    if(!day)return;

    this.filteredTimeSlots = this.timeSlots.filter( timeslot=>{
      if(!timeslot) return;
      return timeslot.weekdayNr == day.id;
    })
    this.timeSlotsFormControl.reset();
  }
}
