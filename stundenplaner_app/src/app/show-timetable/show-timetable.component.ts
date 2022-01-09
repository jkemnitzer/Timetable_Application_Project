import {Component, OnInit} from '@angular/core';
import {HttpService} from "../http/http.service";
import {DialogType, TimetableFileDialogComponent} from "./timetable-file-dialog/timetable-file-dialog.component";
import {MatDialog} from "@angular/material/dialog";


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

  constructor(
    private httpService: HttpService,
    public dialog: MatDialog
  ) {
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
    this.httpService.getRequest(this.base_rel_lectures_path, this.export_timetable_request_options).subscribe(
      (response) => {
        const contentDisposition = response.headers.get('content-disposition');
        let fileName = this.export_timetable_filename_fallback
        if (contentDisposition != null) {
          if (contentDisposition.includes(this.export_timetable_filename_header)) {
            fileName = contentDisposition.split(';')[1].split(this.export_timetable_filename_header)[1];
          }
        }
        const newBlob = new Blob([response.body], { type: this.export_timetable_blob_mimetype })
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
}
