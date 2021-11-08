import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Room } from '../data/room';

export enum DialogType {
  ADD,
  EDIT,
}
export interface DialogRoomData {
  room: Room;
  dialogType: DialogType;
}

@Component({
  selector: 'app-edit-room-dialog',
  templateUrl: './edit-room-dialog.component.html',
  styleUrls: ['./edit-room-dialog.component.css'],
})
export class EditRoomDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<EditRoomDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogRoomData
  ) {}

  ngOnInit(): void {}

  cancelDialog(): void {
    this.dialogRef.close();
  }
}
