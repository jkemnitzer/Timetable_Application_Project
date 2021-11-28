import {Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {Lesson} from "../show-timetable.component";


@Component({
  selector: 'app-timetable-day',
  templateUrl: './timetable-day.component.html',
  styleUrls: ['./timetable-day.component.css']
})
export class TimetableDayComponent implements OnInit, OnChanges{

  @Input() weekday = '';
  @Input() filter = '';
  @Input() data: Lesson[] = [];
  dataSource:MatTableDataSource<Lesson> = new MatTableDataSource<Lesson>();

  displayedColumns: string[] = [ 'start', 'end',
    'lecturer', 'room', 'name', 'edit', 'delete', 'view', 'info'];


  @ViewChild(MatSort) sort: MatSort = new MatSort();

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
    if(row.note!!){
      return 'error'
    }
    if(row.note == 'warning'){
      return 'warning';
    }
     return '';
  }
}
