import { Component, OnInit } from '@angular/core';
import {MOCK_ROOMS} from "../mocks/rooms";

@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.css']
})
export class RoomsComponent implements OnInit {

  rooms = MOCK_ROOMS
  title = 'Räume'

  constructor() { }

  ngOnInit(): void {
  }

}
