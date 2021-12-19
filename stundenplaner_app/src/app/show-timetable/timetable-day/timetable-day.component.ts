import {Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {Lesson} from "../show-timetable.component";
import {HttpService} from "../../http/http.service";


@Component({
  selector: 'app-timetable-day',
  templateUrl: './timetable-day.component.html',
  styleUrls: ['./timetable-day.component.css']
})
export class TimetableDayComponent implements OnInit, OnChanges{

  BASE_DELETE_LESSON_URL = '/timetable/lesson/';

  @Input() weekday = '';
  @Input() filter = '';
  @Input() data: Lesson[] = [];
  dataSource:MatTableDataSource<Lesson> = new MatTableDataSource<Lesson>();

  displayedColumns: string[] = [ 'start', 'end',
    'lecturer', 'room', 'name', 'edit', 'delete', 'view', 'info'];


  @ViewChild(MatSort) sort: MatSort = new MatSort();

  constructor(private httpService: HttpService,) {
  }

  ngOnChanges(changes: SimpleChanges) {
    if(!!changes.data){
      this.dataSource.data = changes.data.currentValue;
    }
    if(!!changes.filter){
      this.dataSource.filter = changes.filter.currentValue;
    }
    this.dataSource.sort = this.sort;

  }


  ngOnInit(): void {
    this.dataSource.data = this.data;
    this.dataSource.filter = this.filter;
    this.dataSource.sort = this.sort;
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
}
