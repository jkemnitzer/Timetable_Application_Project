import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {HttpService} from "../../http/http.service";
import {MatSort, Sort} from "@angular/material/sort";
import {UserRole} from "../data/user-role";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {Util} from "../../common/util";
import {MatDialog} from "@angular/material/dialog";
import {ShowPermissionsDialogComponent} from "../show-permissions-dialog/show-permissions-dialog.component";
import {Permission} from "../data/permission";

@Component({
  selector: 'app-user-overview',
  templateUrl: './user-role-overview.component.html',
  styleUrls: ['../../global.css', './user-role-overview.component.css']
})
export class UserRoleOverviewComponent implements OnInit {

  // the datasource-obj for the mat-table
  dataSource: MatTableDataSource<Array<UserRole>> = new MatTableDataSource<Array<UserRole>>();
  // strings of given columns to show in the mat-table
  columnsToDisplay: string[] = ['actions', 'id', 'type', 'permissions'];
  // the relative path to fetch data from
  base_rel_roles_path: string = '/roles';

  // The sorting-obj attached to the datasource
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _liveAnnouncer: LiveAnnouncer,
    private httpService: HttpService,
    public dialog: MatDialog
  ) { }

  /** ALC **/

  ngOnInit() {
    // fetch the roles-data
    this.fetchAllRoles();
  }

  /** Functions **/

  /**
   * Fetches all available user-roles from the api-service.
   */
  private fetchAllRoles() {
    // fetch the table-datasource
    this.httpService.getRequest(this.base_rel_roles_path).subscribe(
      (response) => {
        console.log(response);
        this.dataSource = new MatTableDataSource(response);
        this.dataSource.sort = this.sort;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  /**
   * Announce the change in sort state for assistive technology.
   *
   * @param sortState current sort-sate of the user-role-table
   */
  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction} ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  /**
   * Applies the filter from the user while triggering the keyboard-event.
   *
   * @param event the KeyboardEvent triggered by the users keyboard-input
   */
  filter(event: KeyboardEvent) {
    this.dataSource.filter = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }


  /**
   * Returns a formatted string with all filterable-columns.
   *
   * @returns string a formatted string containing partial information about filterable-columns
   */
  strElementsToFilter(elements: string[], or: string) : string {
    return elements.slice(0, elements.length - 1).join(', ').concat(
      ' ' + or + ' ', elements[elements.length - 1]
    );
  }

  /**
   * Builds a formatted string to give a quick overview about a users-role-permission
   *
   * @param userRole the userRole-object to be based on
   *
   * @return string a formatted string listing permission id's if available
   */
  strListedIDPermissions(userRole: UserRole) : string {
    // if no permissions are given return empty
    if (!(userRole.permissions.length > 0)) {
      return ' - ';
    }
    // else build and sort the id array
    let permissionIds = new Array<bigint>();
    userRole.permissions.forEach((p: Permission) => {
      permissionIds.push(p.id);
    })
    permissionIds.sort();
    return permissionIds.join(', ');
  }

  /**
   * Return whether given user-role has permissions
   *
   * @param userRole the userRole-object to be based on
   *
   * @return boolean true when there are no permissions
   */
  hasNoPermissions(userRole: UserRole): boolean {
    return !(userRole.permissions.length > 0);
  }

  /**
   * Opens the ShowPermissionsDialogComponent to present information about given user-role
   *
   * @param userRole the user-role to present more information about
   */
  showPermissions(userRole: UserRole) {
    const _userRole = Util.copyObject(userRole);
    this.dialog.open(
      ShowPermissionsDialogComponent, {
        data: {
          userRole: _userRole
        }}
    );
  }
}
