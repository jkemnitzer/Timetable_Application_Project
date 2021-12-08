import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTable, MatTableDataSource } from '@angular/material/table';

import { HttpService } from '../http/http.service';
import { MatDialog } from '@angular/material/dialog';
import { Util } from '../common/util';
import {User} from "./data/user";
import {DialogType, EditUsersDialogComponent} from "./edit-users-dialog/edit-users-dialog.component";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {
  displayedColumns: string[] = ['actions', 'id', 'username', 'title', 'firstName', 'lastName','email','status'];

  dataSource!: MatTableDataSource<User>;
  editEnabled: boolean = false;
  @ViewChild(MatTable) table!: MatTable<User>;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(public dialog: MatDialog, private requestMaker: HttpService) {}

  ngOnInit(): void {
    this.fetchAllUsers();
  }

  openDialog(dialogType: DialogType, user: User): void {
    const dialogRef = this.dialog.open(EditUsersDialogComponent, {
      data: { user: Util.copyObject(user), dialogType: dialogType },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result == undefined) return;
      if (result.dialogType == DialogType.ADD) {
        this.requestMaker.postRequest('/users', result.user).subscribe(() => {
          this.fetchAllUsers()
        });
      } else if (result.dialogType == DialogType.EDIT) {
        this.requestMaker
          .updateRequest(`/users/${result.user.id}`, result.room)
          .subscribe(() => {
            this.fetchAllUsers()
          });
      }
    });
  }

  addRow() {
    this.openDialog(DialogType.ADD, {
      id: 200,
      username: '',
      title: '',
      firstName: '',
      lastName: '',
      email: '',
      status: '',
    });
  }

  editRow(user: User) {
    this.openDialog(DialogType.EDIT, user);
  }

  removeRow(user: User) {
    this.requestMaker.deleteRequest(`/rooms/${user.id}`).subscribe(
      () => {
        this.fetchAllUsers()
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

  private fetchAllUsers() {
    this.requestMaker.getRequest('/users').subscribe(
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
