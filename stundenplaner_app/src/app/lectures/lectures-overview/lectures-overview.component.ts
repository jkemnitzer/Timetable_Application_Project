/**
 * Author(s): Jan Gaida
 * Package(s): #6077
 */

import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {HttpService} from "../../http/http.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort, Sort} from "@angular/material/sort";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {Lecture} from "./data/lecture";
import {Util} from "../../common/util";
import {MatDialog} from "@angular/material/dialog";
import {DialogType, EditLectureDialogComponent} from "../edit-lecture-dialog/edit-lecture-dialog.component";
import {Lesson} from "./data/lesson";

// type of the callback-used when the edit-lecture-dialog closes
type CloseLectureDialogCallback = (lecture: Lecture) => void;

@Component({
  selector: 'app-lectures',
  templateUrl: './lectures-overview.component.html',
  styleUrls: ['./lectures-overview.component.css']
})
export class LecturesOverviewComponent implements OnInit, AfterViewInit {

  /** Variables **/

  // the datasource-obj for the mat-table
  dataSource: MatTableDataSource<Lecture> = new MatTableDataSource<Lecture>();
  // strings of given columns to show in the mat-table
  columnsToDisplay: string[] = ['id', 'name', 'lessons', 'actions'];
  // the relative path to fetch data from
  base_rel_lectures_path: string = '/lectures';
  // displayed filterable column-names
  strColumnsToFilter: string[] = ['Id', 'Vorlesung'];
  // divider used to format filterable column-names; [1] only for last element, [0] for the rest
  dividerElementsToFilter: string[] = [', ', ' oder '];
  // whether the table should be displayed in editing-mode
  editEnabled: Boolean = false;

  // the paginator attached to the table
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  // The sorting-obj attached to the datasource
  @ViewChild(MatSort) sort!: MatSort;

  /**
   * Constructor
   *
   * @param _liveAnnouncer the Announcer service triggered when the table-sort-logic has changed
   * @param httpService the HttpService to fetch data from
   * @param dialog the Dialog which will be opened/closed upon certain user-actions
   */
  constructor(
    private _liveAnnouncer: LiveAnnouncer,
    private httpService: HttpService,
    public dialog: MatDialog
  ) { }

  /** ALC **/

  ngOnInit() {
    // fetch the lecture-data
    this.fetchAllLectures();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  /** Functions **/

  /**
   * Fetches all available lectures from the api-service.
   */
  private fetchAllLectures() {
    // fetch the table-datasource
    this.httpService.getRequest(this.base_rel_lectures_path).subscribe(
      (response) => {
        console.log(response);
        this.dataSource = new MatTableDataSource(response);
        this.dataSource.sort = this.sort;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  /**
   * Announce the change in sort state for assistive technology.
   *
   * @param sortState current sort-sate of the lecture-table
   */
  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction} ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  /**
   * Applies the filter from the user while triggering the keyboard-event.
   *
   * @param event the KeyboardEvent triggered by the users keyboard-input
   */
  filter(event: KeyboardEvent) {
    this.dataSource.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  /**
   * Returns a formatted string with all filterable-columns.
   *
   * @returns string a formatted string containing partial information about filterable-columns
   */
  strElementsToFilter() : string {
    return this.strColumnsToFilter.slice(0, this.strColumnsToFilter.length - 1).join(this.dividerElementsToFilter[0]).concat(
      this.dividerElementsToFilter[1], this.strColumnsToFilter[this.strColumnsToFilter.length - 1]
    );
  }

  /**
   * Transforms the given lessons into a simple formatted string containing their names
   *
   * @param lessons the lessons to format
   */
  strLessons(lessons: Lesson[]) : string {
    if (lessons.length > 1) { // more than one lesson
      let str = lessons[0].lessonName
      for (let i = 1; i < lessons.length; i++) {
        str += ', ' + lessons[i].lessonName
      }
      return str
    }
    else if (lessons.length == 1) { // just one lesson
      return lessons[0].lessonName
    }
    return '' // no lesson
  }

  /**
   * Opens the EditLectureDialogComponent to present given lecture in this component
   *
   * @param dialogType the dialog-type-enum to open
   * @param _lecture the original lecture object to open the dialog with
   * @param onCloseCallback the callback-function to trigger after the dialog is closed
   */
  private showDialog(dialogType: DialogType, _lecture: Lecture, onCloseCallback: CloseLectureDialogCallback) {
    // deep-copy lecture
    const lecture = Util.copyObject(_lecture);
    // open dialog
    this.dialog.open(
      EditLectureDialogComponent, {
        data: {
          dialogType: dialogType,
          lecture: lecture
      }}
    // if not canceled trigger onCloseCallback
    ).afterClosed().subscribe( (result) => {
      if (!(result == undefined)) {
        onCloseCallback(result.lecture)
      }
    },
      (error) => {
        console.error(error);
      });
  }

  /** CRUD **/

  /**
   * Toggles the editing state used in the table
   */
  toggleEditing() {
    this.editEnabled = !this.editEnabled;
  }

  /**
   * Will show the Edit-Dialog for a new Lecture-Object and fetch all lectures after closing the dialog
   */
  createLecture() {
    this.showDialog(
      DialogType.CREATE,
      {
        id: 0,
        lectureName: '',
        lessons: []
      },
      (resulting_lecture) => {
        this.httpService.postRequest(
          `${this.base_rel_lectures_path}`, resulting_lecture
        ).subscribe(() => {
          this.fetchAllLectures();
        });
      }
    );
  }

  /**
   * Shows the Edit-dialog for the lectures and will fetch all lectures after closing the dialog
   *
   * @param lecture the lecture to edit
   */
  editLecture(lecture: Lecture) {
    this.showDialog(
      DialogType.EDIT,
      lecture,
      (resulting_lecture) => {
        this.httpService.updateRequest(
          `${this.base_rel_lectures_path}/${resulting_lecture.id}`, resulting_lecture
        ).subscribe(() => {
          this.fetchAllLectures();
        });
      }
    );
  }

  /**
   * Sends a delete request for given lecture and fetches the table again.
   *
   * @param lecture the lecture to remove
   */
  removeLecture(lecture: Lecture) {
    this.httpService.deleteRequest(`${this.base_rel_lectures_path}/${lecture.id}`).subscribe(
    () => {
      this.fetchAllLectures();
    },
    (error) => {
      console.error(error);
    });
  }
}

