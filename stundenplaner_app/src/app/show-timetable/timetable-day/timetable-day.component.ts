import {Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {MatSort, Sort} from "@angular/material/sort";
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
    'lecturer', 'room', 'name', 'edit', 'delete', 'info'];


  @ViewChild(MatSort) sort: MatSort = new MatSort();

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
}
function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
