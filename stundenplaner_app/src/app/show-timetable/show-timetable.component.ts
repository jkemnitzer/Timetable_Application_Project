import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpService} from "../http/http.service";
import {MatSelect} from "@angular/material/select";
import {Program} from "../programs/program-overview/data/program";
import {Semester} from "../programs/program-overview/data/semester";
import {QueryParam} from "../http/query-param";
import {Version} from "./data/version";
import {DialogType, TimetableFileDialogComponent} from "./timetable-file-dialog/timetable-file-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {ErrorSnackbarComponent} from "./error-snackbar/error-snackbar.component";
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormControl, Validators} from "@angular/forms";
import {Observable} from "rxjs";
import {SuccessSnackbarComponent} from "./success-snackbar/success-snackbar.component";
import {map, startWith} from "rxjs/operators";


export class TimeTable {
  version: Version|null = null;
  lessons: Lesson[] = [];
}
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
export class Room {
  id: Number = 0;
  number: String = '';
  building: String = '';
}
export class Lecture {
  id: Number = 0;
  lectureName: String = '';
}
export class Lesson {
  name: String = '';
  endTime: Date = new Date();
  startTime: Date = new Date();
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
export class TimeSlot {
  id: Number = 0;
  start: String = '';
  end: String = '';
  weekdayNr: Number = 0;
}
export class Day {
  id: Number = 0;
  name: String = '';
}

@Component({
  selector: 'app-show-timetable',
  templateUrl: './show-timetable.component.html',
  styleUrls: ['../global.css', './show-timetable.component.css']
})
export class ShowTimetableComponent implements OnInit {

  @ViewChild("semesterSelect") semesterSelect!: MatSelect;

  mondayData: Lesson[] = [];
  tuesdayData: Lesson[] = [];
  wednesdayData: Lesson[] = [];
  thursdayData: Lesson[] = [];
  fridayData: Lesson[] = [];
  saturdayData: Lesson[] = [];

  BASE_TIMETABLE_URL: string = '/timetable/';
  BASE_ADD_LESSON_URL: string = '/timetable/lesson';
  BASE_LECTURERS_URL: string = '/users';
  BASE_ROOMS_URL: string = '/rooms';
  BASE_LECTURES_URL: string = '/lectures';
  BASE_TIMESLOTS_URL: string = '/timeSlots';
  filter: string = '';



  faculties = [
    {name: 'Informatik'},
    {name: 'Ingenieurwissenschaften'},
    {name: 'Interdisziplinäre und innovative Wissenschaften'},
    {name: 'Wirtschaftswissenschaften'},
  ];
  selectedFaculty = this.faculties[1];
  lessonNameFormControl = new FormControl();


  lecturesFormControl = new FormControl('', [Validators.required]);
  days: Day[] = [
    {name: 'Montag', id: 1},
    {name: 'Dienstag', id: 2},
    {name: 'Mittwoch', id: 3},
    {name: 'Donnerstag', id: 4},
    {name: 'Freitag', id: 5},
    {name: 'Samstag', id: 6},
  ];
  isCreateLessonVisible: boolean = false;
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

  programs: Program[] = [];
  semesters: Semester[] = [];
  versions: Version[] = [];
  selectedProgram: Program = {} as Program;
  selectedSemester: Semester = {} as Semester;
  selectedVersion: Version = {} as Version;

  constructor(public dialog: MatDialog,
              private httpService: HttpService,
              private _snackBar: MatSnackBar) {

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

  ngOnInit(): void {
    this.loadVersions();

    this.filteredRooms = this.roomFormControl.valueChanges.pipe(
      startWith(''),
      map((input: string) => (input ? this.filterRooms(input) : this.rooms.slice())));
    this.filteredLecturers = this.lecturerFormControl.valueChanges.pipe(
      startWith(''),
      map(name => (name ? this.filterLecturer(name) : this.lecturers.slice())));
    this.filteredLectures = this.lecturesFormControl.valueChanges.pipe(
      startWith(''),
      map((input: string) => (input ? this.filterLectures(input) : this.lectures.slice())));

    this.loadPrograms();
    this.getTimetable();
    this.loadTimetable()
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

  private showSuccess(message: string) {
    this._snackBar.openFromComponent(SuccessSnackbarComponent, {
      duration:5000,
      data: message
    },);
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

  closeCreateLesson() {
    this.isCreateLessonVisible = false;
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



  // a reference to the DialogType-enum to access in the html-file
  DialogType = DialogType;
  // the relative path to fetch the timetable from
  base_rel_lectures_path: string = '/timetable/export';
  // the request options when attempting to download
  export_timetable_request_options: object = { responseType: 'blob' as 'json', observe: 'response'}
  // the mimetype of the downloaded timetable
  export_timetable_blob_mimetype: string = 'application/octet-stream'
  // the filename-identifier in the responses-header content-disposition
  export_timetable_filename_header: string = 'fileName='
  // the fallback-filename for the exported timetable
  export_timetable_filename_fallback: string = 'timetable_unknown.xls'
  applyFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.filter = filterValue.trim().toLowerCase();
  }
  private containsErrors() {
    return !!this.lecturesFormControl.errors ||
      !!this.daysFormControl.errors ||
      !!this.timeSlotsFormControl.errors ||
      !!this.roomFormControl.errors ||
      !!this.lecturerFormControl.errors ||
      !!this.typeFormControl.errors;
  }

  private touchAllInputs() {
    this.timeSlotsFormControl.markAsTouched()
    this.lecturesFormControl.markAsTouched();
    this.daysFormControl.markAsTouched();
    this.lecturerFormControl.markAsTouched();
    this.roomFormControl.markAsTouched();
    this.typeFormControl.markAsTouched();
    this.lessonNameFormControl.markAsTouched();
  }

  private createNewLesson(lesson: Lesson): Lesson | null {
    let returnedLesson = null;
    this.httpService.postRequest(this.BASE_ADD_LESSON_URL, lesson).subscribe(
      (response) => {

        returnedLesson = response;
        returnedLesson.startTime = new Date('December 17, 1995 ' + returnedLesson.startTime.toString());
        returnedLesson.endTime = new Date('December 17, 1995 ' + returnedLesson.endTime.toString());

        if (returnedLesson.weekdayNr == 1) this.mondayData = this.addToObservedArray(this.mondayData, returnedLesson);
        else if (returnedLesson.weekdayNr == 2) this.tuesdayData = this.addToObservedArray(this.tuesdayData, returnedLesson);
        else if (returnedLesson.weekdayNr == 3) this.wednesdayData = this.addToObservedArray(this.wednesdayData, returnedLesson);
        else if (returnedLesson.weekdayNr == 4) this.thursdayData = this.addToObservedArray(this.thursdayData, returnedLesson);
        else if (returnedLesson.weekdayNr == 5) this.fridayData = this.addToObservedArray(this.fridayData, returnedLesson);
        else if (returnedLesson.weekdayNr == 6) this.saturdayData = this.addToObservedArray(this.saturdayData, returnedLesson);
        this.showSuccess('added');
      },
      () => {
        this.showError()
      }
    );


    return returnedLesson;
  }
  addToObservedArray(array: Lesson[], lesson: Lesson): Lesson[]{
    const newArray: Lesson[] = array.slice();
    newArray.push(lesson);
    return newArray;
  }
  openCreateLesson() {
    this.loadLecturers();
    this.loadRooms();
    this.loadLectures();
    this.loadTimeslots();
    this.isCreateLessonVisible = true;
  }
  private loadLecturers() {
    this.httpService.getRequest(this.BASE_LECTURERS_URL).subscribe(
      (response) => {
        this.lecturers = response;
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
      },
      (error) => {
        console.error(error);
      }
    );
  }


  generateTimetable(){
    //TODO: Wait for Backend
    /*this.httpService.getRequest('/generate').subscribe(
          (response) => {
            const mondayTemp: Lesson[] = [];
            const tuesdayTemp: Lesson[] = [];
            const wednesdayTemp: Lesson[] = [];
            const thursdayTemp: Lesson[] = [];
            const fridayTemp: Lesson[] = [];
            const saturdayTemp: Lesson[] = [];
            response.forEach((lesson: Lesson) => {
              if (lesson.weekdayNr == 1) mondayTemp.push(lesson);
              else if (lesson.weekdayNr == 2) tuesdayTemp.push(lesson);
              else if (lesson.weekdayNr == 3) wednesdayTemp.push(lesson);
              else if (lesson.weekdayNr == 4) thursdayTemp.push(lesson);
              else if (lesson.weekdayNr == 5) fridayTemp.push(lesson);
              else if (lesson.weekdayNr == 6) saturdayTemp.push(lesson);
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
        );*/
  }


  addLesson() {
    if(this.containsErrors()){
      this.touchAllInputs();
      return;
    }
    const lesson = new Lesson();
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
    lesson.versionId = this.selectedVersion.id;
    console.log(this.selectedVersion);
    this.createNewLesson(lesson);
    this.timeSlotsFormControl.reset();
  }

  getTimetable() {
    this.httpService.getRequest(this.BASE_TIMETABLE_URL + this.selectedVersion?.id).subscribe(
      (response) => {
        const mondayTemp: Lesson[] = [];
        const tuesdayTemp: Lesson[] = [];
        const wednesdayTemp: Lesson[] = [];
        const thursdayTemp: Lesson[] = [];
        const fridayTemp: Lesson[] = [];
        const saturdayTemp: Lesson[] = [];
        response.lessons.forEach((lesson: Lesson) => {
          lesson.startTime = new Date('December 17, 1995 ' + lesson.startTime.toString());
          lesson.endTime = new Date('December 17, 1995 ' + lesson.endTime.toString());

          if (lesson.weekdayNr == 1) mondayTemp.push(lesson);
          else if (lesson.weekdayNr == 2) tuesdayTemp.push(lesson);
          else if (lesson.weekdayNr == 3) wednesdayTemp.push(lesson);
          else if (lesson.weekdayNr == 4) thursdayTemp.push(lesson);
          else if (lesson.weekdayNr == 5) fridayTemp.push(lesson);
          else if (lesson.weekdayNr == 6) saturdayTemp.push(lesson);
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

  loadTimetable(filter: QueryParam[] = []): void {
    let url = this.BASE_TIMETABLE_URL;
    if (filter.length > 0) {
      url += "?";
      filter.forEach(param => {
        url += param.key + "=" + param.value + "&"
      })
      url = url.substr(0, url.length - 1) //cut trailing &
    }
    this.httpService.getRequest(url).subscribe(
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

          if (lesson.weekdayNr == 1) mondayTemp.push(lesson);
          else if (lesson.weekdayNr == 2) tuesdayTemp.push(lesson);
          else if (lesson.weekdayNr == 3) wednesdayTemp.push(lesson);
          else if (lesson.weekdayNr == 4) thursdayTemp.push(lesson);
          else if (lesson.weekdayNr == 5) fridayTemp.push(lesson);
          else if (lesson.weekdayNr == 6) saturdayTemp.push(lesson);
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
loadPrograms() {
  this.programs = [];
  this.httpService.getRequest("/programs").subscribe(
    (response) => {
      this.programs = response;
      this.semesterSelect.disabled = true;
    }
  )
}



  loadSemesters() {
    this.semesters = [];
    this.httpService.getRequest("/programs/" + this.selectedProgram.id + "/semester").subscribe(
      (response) => {
        this.semesters = response;
        if (this.semesters.length > 0) {
          this.semesterSelect.disabled = false;
        }
      }
    )
  }

  loadVersions() {
    this.versions = [];
    this.httpService.getRequest("/timetable/versions").subscribe(
      (response) => {
        this.versions = response;
        if(this.versions && this.versions.length > 0) {
          this.selectedVersion = this.versions[0]
        }
      }
    );

  }

  reloadFilteredTimetable() {
    let filter: QueryParam[] = [];
    if (this.selectedProgram.id != undefined) filter.push({key: "program", value: this.selectedProgram.id + ""});
    if (this.selectedSemester.id != undefined) filter.push({key: "semester", value: this.selectedSemester.id + ""});
    if (this.selectedVersion.id != undefined) filter.push({key: "version", value: this.selectedVersion.id + ""});
    this.loadTimetable(filter);
  }

  showError() {
    this._snackBar.openFromComponent(ErrorSnackbarComponent, {
      duration: 5000,
    });
  }

  /**
   * Opens the File-Dialog to offer the User the option to i.e. upload a timetable
   *
   * @param dialogType the type of the dialog to present
   */
  openFileDialog(dialogType: DialogType): void {
    this.dialog.open(
      TimetableFileDialogComponent, {
        data: {dialogType: dialogType}
      }
    )
  }

  /**
   * Causes to download of the exported timetable via the api
   */
  downloadTimetable(): void {
    this.httpService.getRequest(this.base_rel_lectures_path).subscribe(
      (response) => {
        const contentDisposition = response.headers.get('content-disposition');
        let fileName = this.export_timetable_filename_fallback
        if (contentDisposition != null) {
          if (contentDisposition.includes(this.export_timetable_filename_header)) {
            fileName = contentDisposition.split(';')[1].split(this.export_timetable_filename_header)[1];
          }
        }
        const newBlob = new Blob([response.body], {type: this.export_timetable_blob_mimetype})
        // create and trigger hyperlink
        let downloadLink = document.createElement('a');
        let url = URL.createObjectURL(newBlob);
        //if Safari open in new window.
        if (navigator.userAgent.indexOf('Safari') != -1) {
          downloadLink.setAttribute('target', '_blank');
        }
        downloadLink.setAttribute('href', url);
        downloadLink.setAttribute('download', fileName);
        downloadLink.style.visibility = 'hidden';
        document.body.appendChild(downloadLink);
        downloadLink.click();
        document.body.removeChild(downloadLink);
      });
  }

  resetSelections() {
    this.selectedProgram = {} as Program;
    this.selectedSemester = {} as Semester;
    this.selectedVersion = {} as Version;
    this.reloadFilteredTimetable();
  }

  resetFilter() {
    this.filter = "";
  }

  addTimeTable() {
    const timetable = new TimeTable();

    timetable.version = {id: 0,
      semesterYear: '',
      version: '',
      comment: 'created from Frontend'};
    this.httpService.postRequest(this.BASE_TIMETABLE_URL, timetable).subscribe(
      () => {

        this.loadVersions();
      },
      () => {
        this.showError()
      }
    );

  }
}
