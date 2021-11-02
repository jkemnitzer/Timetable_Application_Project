import {Component, Input, OnInit} from '@angular/core';


@Component({
  selector: 'app-timetable-day',
  templateUrl: './timetable-day.component.html',
  styleUrls: ['./timetable-day.component.css']
})
export class TimetableDayComponent implements OnInit {

  @Input() weekday = '';
  @Input() data: any = null;

  displayedColumns: string[] = [ 'start', 'end',
    'lecturer', 'room', 'name', 'edit', 'delete', 'view'];
  constructor() {
  }

  ngOnInit(): void {
  }

}
