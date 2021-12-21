import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { MOCK_ROOMS } from '../mocks/rooms';
import { Room } from './data/room';
import { HttpService } from '../http/http.service';
import { MatDialog } from '@angular/material/dialog';
import {
  DialogType,
  EditRoomDialogComponent,
} from './edit-room-dialog/edit-room-dialog.component';
import { Util } from '../common/util';

@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['../global.css', './rooms.component.css'],
})
export class RoomsComponent implements OnInit {
  displayedColumns: string[] = ['actions', 'id', 'roomNumber', 'building'];
  dataSource!: MatTableDataSource<Room>;
  editEnabled: boolean = false;
  @ViewChild(MatTable) table!: MatTable<Room>;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(public dialog: MatDialog, private requestMaker: HttpService) {}

  ngOnInit(): void {
    this.fetchAllRooms();
  }

  openDialog(dialogType: DialogType, room: Room): void {
    const dialogRef = this.dialog.open(EditRoomDialogComponent, {
      data: { room: Util.copyObject(room), dialogType: dialogType },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result == undefined) return;
      if (result.dialogType == DialogType.ADD) {
        this.requestMaker.postRequest('/rooms', result.room).subscribe(() => {
          this.fetchAllRooms();
        });
      } else if (result.dialogType == DialogType.EDIT) {
        this.requestMaker
          .updateRequest(`/rooms/${result.room.id}`, result.room)
          .subscribe(() => {
            this.fetchAllRooms();
          });
      }
    });
  }

  addRow() {
    this.openDialog(DialogType.ADD, {
      id: 200,
      number: '',
      building: '',
    });
  }

  editRow(room: Room) {
    this.openDialog(DialogType.EDIT, room);
  }

  removeRow(room: Room) {
    this.requestMaker.deleteRequest(`/rooms/${room.id}`).subscribe(
      () => {
        this.fetchAllRooms();
      },
      (error) => {
        console.error(error);
      }
    );
  }

  toggleEditing() {
    this.editEnabled = !this.editEnabled;
  }

  applyFilter(event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  private fetchAllRooms() {
    this.requestMaker.getRequest('/rooms').subscribe(
      (response) => {
        this.dataSource = new MatTableDataSource(response);
        this.dataSource.sort = this.sort;
        console.log(response);
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
