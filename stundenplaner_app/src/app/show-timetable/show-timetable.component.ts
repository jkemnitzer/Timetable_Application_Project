import {Component, Input, OnInit} from '@angular/core';
import {FlatTreeControl} from "@angular/cdk/tree";
import {MOCK_SHEDULE} from '../mocks/shedule';

interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
}

interface Lesson {
  name: string;
  weekday: number;
  startHour: number;
  startMinutes: number;
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

  mondayData: Lesson[] = [];
  tuesdayData: Lesson[] = [];
  wednesdayData: Lesson[] = [];
  thursdayData: Lesson[] = [];
  fridayData: Lesson[] = [];
  saturdayData: Lesson[] = [];


  treeControl = new FlatTreeControl<ExampleFlatNode>(
    node => node.level, node => node.expandable);

  constructor() { }

  ngOnInit(): void {
    MOCK_SHEDULE.forEach(lesson  => {
      if(lesson.weekday == 0) this.mondayData.push(lesson);
      else if (lesson.weekday == 1) this.tuesdayData.push(lesson);
      else if (lesson.weekday == 2) this.wednesdayData.push(lesson);
      else if (lesson.weekday == 3) this.thursdayData.push(lesson);
      else if (lesson.weekday == 4) this.fridayData.push(lesson);
      else if (lesson.weekday == 5) this.saturdayData.push(lesson);


    })
  }

}
