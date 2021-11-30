/**
 * Author(s): Jan Gaida
 * Package(s): #6145
 */

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {UserRole} from "../data/user-role";
import {Permission} from "../data/permission";
import {MatTableDataSource} from "@angular/material/table";

/**
 * the data-object for this dialog-component
 */
export interface DialogPermissionData {
  userRole: UserRole;
}

/**
 * the dialog-component displayed to give more information about given user-permissions
 */
@Component({
  selector: 'app-show-permissions-dialog',
  templateUrl: './show-permissions-dialog.component.html',
  styleUrls: ['./show-permissions-dialog.component.css'],
})
export class ShowPermissionsDialogComponent implements OnInit {

  // the datasource-obj for the mat-table
  dataSource: MatTableDataSource<Permission> = new MatTableDataSource<Permission>();
  // strings of given columns to show in the mat-table
  columnsToDisplay: string[] = ['id', 'type'];

  /**
   * Constructor
   *
   * @param dialogRef the dialog-object passed to self
   * @param data the data-object used in this component
   */
  constructor(
    public dialogRef: MatDialogRef<ShowPermissionsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogPermissionData
  ) {
    // init the data-source; no need to further adjust these datasource since CRUD is not present
    this.dataSource = new MatTableDataSource<Permission>(data.userRole.permissions);
  }

  /** ALC **/

  ngOnInit(): void {}

  /** Functions **/

  /**
   * Helper-Function to return the title for managed userRole
   *
   * @return string: the name to display as dialog-component-title
   */
  getTitle(): string {
    return String(this.data.userRole.type);
  }

  /**
   *  Delegates the close-call to the dialog-reference
   */
  closeDialog(): void {
    this.dialogRef.close();
  }
}
