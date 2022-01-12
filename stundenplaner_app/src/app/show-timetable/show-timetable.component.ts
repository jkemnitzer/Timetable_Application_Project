import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpService} from "../http/http.service";
import {MatSelect} from "@angular/material/select";
import {Program} from "../programs/program-overview/data/program";
import {Semester} from "../programs/program-overview/data/semester";
import {QueryParam} from "../http/query-param";
import {Lesson} from "./data/lesson";
import {Version} from "./data/version";
import {DialogType, TimetableFileDialogComponent} from "./timetable-file-dialog/timetable-file-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {ErrorSnackbarComponent} from "./error-snackbar/error-snackbar.component";
import {MatSnackBar} from '@angular/material/snack-bar';

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

  DialogType = DialogType;
  base_rel_lectures_path: string = '/timetable/export';
  export_timetable_request_options: object = {responseType: 'blob' as 'json', observe: 'response'}
  export_timetable_blob_mimetype: string = 'application/octet-stream'
  export_timetable_filename_header: string = 'fileName='
  export_timetable_filename_fallback: string = 'timetable_unknown.xls'

  mondayData: Lesson[] = [];
  tuesdayData: Lesson[] = [];
  wednesdayData: Lesson[] = [];
  thursdayData: Lesson[] = [];
  fridayData: Lesson[] = [];
  saturdayData: Lesson[] = [];

  BASE_TIMETABLE_URL: string = '/timetable';

  filter: string = '';

  days: Day[] = [
    {name: 'Montag', id: 1},
    {name: 'Dienstag', id: 2},
    {name: 'Mittwoch', id: 3},
    {name: 'Donnerstag', id: 4},
    {name: 'Freitag', id: 5},
    {name: 'Samstag', id: 6},
  ];

  programs: Program[] = [];
  semesters: Semester[] = [];
  versions: Version[] = [];
  selectedProgram: Program = {} as Program;
  selectedSemester: Semester = {} as Semester;
  selectedVersion: Version = {} as Version;

  constructor(public dialog: MatDialog,
              private httpService: HttpService,
              private _snackBar: MatSnackBar) {
    this.getTimetable();
    this.loadTimetable()
  }

  ngOnInit(): void {
    this.loadVersions();
    this.loadPrograms();
  }


  applyFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.filter = filterValue.trim().toLowerCase();
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
      }
    )
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
}
