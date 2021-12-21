import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {User} from "../data/user";

export enum DialogType {
  ADD,
  EDIT,
}
export interface DialogUserData {
  user: User;
  dialogType: DialogType;
}

@Component({
  selector: 'app-edit-users-dialog',
  templateUrl: './edit-users-dialog.component.html',
  styleUrls: ['../../global.css', './edit-users-dialog.component.css'],
})
export class EditUsersDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<EditUsersDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogUserData
  ) {}

  ngOnInit(): void {}

  cancelDialog(): void {
    this.dialogRef.close();
  }
}
