import {Component, OnInit} from '@angular/core';
import {MOCK_SHEDULE} from '../mocks/shedule';
import {MatTableDataSource} from "@angular/material/table";

interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
}

export interface Lesson {
  name: string;
  weekday: number;
  start: number;
  length: number;
  lecturer: string;
  room: string;
}

@Component({
  selector: 'app-show-timetable',
  templateUrl: './show-timetable.component.html',
  styleUrls: ['./show-timetable.component.css']
})
export class ShowTimetableComponent implements OnInit {

  mondayData: MatTableDataSource<Lesson> = new MatTableDataSource<Lesson>();
  tuesdayData: MatTableDataSource<Lesson> = new MatTableDataSource<Lesson>();
  wednesdayData: MatTableDataSource<Lesson> = new MatTableDataSource<Lesson>();
  thursdayData: MatTableDataSource<Lesson> = new MatTableDataSource<Lesson>();
  fridayData: MatTableDataSource<Lesson> = new MatTableDataSource<Lesson>();
  saturdayData: MatTableDataSource<Lesson> = new MatTableDataSource<Lesson>();


  constructor() { }

  ngOnInit(): void {
    MOCK_SHEDULE.forEach(lesson  => {
      if(lesson.weekday == 0) this.mondayData.data.push(lesson);
      else if (lesson.weekday == 1) this.tuesdayData.data.push(lesson);
      else if (lesson.weekday == 2) this.wednesdayData.data.push(lesson);
      else if (lesson.weekday == 3) this.thursdayData.data.push(lesson);
      else if (lesson.weekday == 4) this.fridayData.data.push(lesson);
      else if (lesson.weekday == 5) this.saturdayData.data.push(lesson);


    })
  }

  applyFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.mondayData.filter = filterValue.trim().toLowerCase();
  }
}
