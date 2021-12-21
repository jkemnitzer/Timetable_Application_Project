import {Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {MatSort, Sort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {Day, Lesson} from "../show-timetable.component";
import {HttpService} from "../../http/http.service";
import {MatDialog} from "@angular/material/dialog";
import {EditLessonFormComponent} from "../edit-lesson-form/edit-lesson-form.component";


@Component({
  selector: 'app-timetable-day',
  templateUrl: './timetable-day.component.html',
  styleUrls: ['./timetable-day.component.css']
})
export class TimetableDayComponent implements OnInit, OnChanges{

  BASE_DELETE_LESSON_URL = '/timetable/lesson/';

  @Input() weekday: Day = new Day();
  @Input() filter = '';
  @Input() data: Lesson[] = [];
  @Input() siblings: TimetableDayComponent[] = [];
  dataSource:MatTableDataSource<Lesson> = new MatTableDataSource<Lesson>();

  displayedColumns: string[] = [ 'start', 'end',
    'lecturer', 'room', 'name', 'edit', 'delete', 'info'];


  @ViewChild(MatSort) sort: MatSort = new MatSort();

  constructor(private httpService: HttpService, public dialog: MatDialog) {
  }

  ngOnChanges(changes: SimpleChanges) {
    if(!!changes.data){
      this.dataSource.data = changes.data.currentValue;
    }
    if(!!changes.filter){
      this.dataSource.filter = changes.filter.currentValue;
    }
    this.dataSource.sort = this.sort;
    this.sortData(this.dataSource.sort);
  }


  ngOnInit(): void {
    this.dataSource.data = this.data;
    this.dataSource.filter = this.filter;
    this.dataSource.sort = this.sort;
  }
  sortData(sort: Sort) {
    const data = this.dataSource.data.slice();
    if (!sort.active || sort.direction === '') {
      this.dataSource.data = data;
      return;
    }

    this.dataSource.data = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'start':
          return compare(a.startTime.getTime(), b.startTime.getTime(), isAsc);
        default:
          return 0;
      }
    });
  }

  getErrorClass(row: Lesson) {
    if(row.error == 'warning'){
      return 'warning';
    }
    if(row.error!!){
      return 'error'
    }

     return '';
  }

  deleteLesson(lesson: Lesson) {
    let returnedLesson = null;
    console.log(lesson.id);
    this.httpService.deleteRequest(this.BASE_DELETE_LESSON_URL + lesson.id, lesson).subscribe(
      (response) => {
        if(response == null){
          return;
        }

        returnedLesson = response;
        const tempArray = this.dataSource.data.filter(value => value.id != lesson.id);
        console.log(tempArray);
        this.dataSource.data = tempArray;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  editLesson(lesson: Lesson) {
    const dialogRef = this.dialog.open(EditLessonFormComponent, {
      data: {lesson: lesson},
    });


    dialogRef.afterClosed().subscribe(updatedLesson => {
      if(typeof updatedLesson == 'string')return;
      const tempArray = this.dataSource.data.filter(value => value.id != lesson.id);
      if(this.weekday == updatedLesson.weekdayNr) tempArray.push(updatedLesson);
      else this.siblings.forEach(value => value.addLesson(updatedLesson));
      this.dataSource.data = tempArray;
    });
  }

  addLesson(lesson: Lesson){
    if(this.weekday.id == lesson.weekdayNr){
      const tempArray = this.dataSource.data.slice();
      tempArray.push(lesson);
      this.dataSource.data = tempArray;
    }
    if(this.dataSource.sort != null)
      this.sortData(this.dataSource.sort);
  }
}
function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
