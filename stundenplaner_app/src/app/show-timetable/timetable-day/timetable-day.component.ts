import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {Lesson} from "../show-timetable.component";


@Component({
  selector: 'app-timetable-day',
  templateUrl: './timetable-day.component.html',
  styleUrls: ['./timetable-day.component.css']
})
export class TimetableDayComponent implements OnInit {

  @Input() weekday = '';
  @Input() data: MatTableDataSource<Lesson> = new MatTableDataSource<Lesson>();
  dataSource:MatTableDataSource<Lesson> = new MatTableDataSource<any>();

  displayedColumns: string[] = [ 'start', 'end',
    'lecturer', 'room', 'name', 'edit', 'delete', 'view', 'info'];


  constructor() {}

  @ViewChild(MatSort) sort: MatSort = new MatSort();

  ngAfterViewInit() {
    this.dataSource = this.data;
    this.dataSource.sort = this.sort;
  }


  ngOnInit(): void {
  }

  getErrorClass(row: Lesson) {
    if(row.weekday == 0){
      return 'error'
    }
    if(row.weekday == 1){
      return 'warning';
    }
     return '';
  }
}
