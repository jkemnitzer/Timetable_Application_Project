import {Component, OnInit} from '@angular/core';
import {HttpService} from "../http/http.service";


export interface Lesson {
  endTime: Date,
  startTime: Date,
  id: Number,
  lectureId: Number,
  lectureTitle: String,
  lecturer: String,
  lecturerId: Number,
  lessonType: String,
  note: String,
  room: String,
  roomId: Number,
  timeslotId: Number,
  versionId: Number,
  weekdayNr: Number,
  error : String
}


@Component({
  selector: 'app-show-timetable',
  templateUrl: './show-timetable.component.html',
  styleUrls: ['../global.css', './show-timetable.component.css']
})

export class ShowTimetableComponent implements OnInit {


  mondayData: Lesson[] = [];
  tuesdayData: Lesson[] = [];
  wednesdayData: Lesson[] = [];
  thursdayData: Lesson[] = [];
  fridayData: Lesson[] = [];
  saturdayData: Lesson[] = [];

  BASE_TIMETABLE_URL: string = '/timetable';
  filter: string = '';

  faculties = [
    {name: 'Informatik'},
    {name: 'Ingenieurwissenschaften'},
    {name: 'Interdisziplinäre und innovative Wissenschaften'},
    {name: 'Wirtschaftswissenschaften'},
  ];
  selectedFaculty = this.faculties[1];

  majors = [
    {name: 'Allgemeine Informatik'},
    {name: 'Medien Informatik'},
    {name: 'Maschinenbau'},
  ];
  selectedMajor = this.majors[1];

  semesters = [
    {semester: '1'},
    {semester: '2'},
    {semester: '3'},
  ];
  selectedSemester = this.semesters[1];


  constructor(private httpService: HttpService,) {
    // fetch the table-datasource
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

          console.log(lesson.startTime)
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

  ngOnInit(): void {}


  applyFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.filter = filterValue.trim().toLowerCase();
  }
}
