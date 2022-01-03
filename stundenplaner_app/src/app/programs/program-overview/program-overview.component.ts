import { Component, OnInit, ViewChild } from '@angular/core';
import { Program } from './data/program';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import {
  DialogType,
  EditProgramDialogComponent,
} from '../edit-program-dialog/edit-program-dialog.component';
import { HttpService } from '../../http/http.service';
import { Util } from '../../common/util';

@Component({
  selector: 'app-program-overview',
  templateUrl: './program-overview.component.html',
  styleUrls: ['../../global.css','./program-overview.component.css'],
})
export class ProgramOverviewComponent implements OnInit {
  displayedColumns: string[] = ['actions', 'id', 'name'];
  dataSource!: MatTableDataSource<Program>;
  editEnabled: boolean = false;
  @ViewChild(MatTable) table!: MatTable<Program>;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(public dialog: MatDialog, private requestMaker: HttpService) {}

  ngOnInit() {
    this.fetchAllPrograms();
  }

  openDialog(dialogType: DialogType, program: Program): void {
    const dialogRef = this.dialog.open(EditProgramDialogComponent, {
      data: { program: Util.copyObject(program), dialogType: dialogType },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result == undefined) return;
      if (result.dialogType == DialogType.ADD) {
        this.requestMaker
          .postRequest('/programs', result.program)
          .subscribe(() => {
            this.fetchAllPrograms();
          });
      } else if (result.dialogType == DialogType.EDIT) {
        this.requestMaker
          .updateRequest(`/programs/${result.program.id}`, result.program)
          .subscribe(() => {
            this.fetchAllPrograms();
          });
      }
    });
  }

  addRow() {
    this.openDialog(DialogType.ADD, {
      id: 200,
      name: '',
      semesters: [],
    });
  }

  editRow(program: Program) {
    this.openDialog(DialogType.EDIT, program);
  }

  removeRow(program: Program) {
    this.requestMaker.deleteRequest(`/programs/${program.id}`).subscribe(
      () => {
        this.fetchAllPrograms();
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

  private fetchAllPrograms() {
    this.requestMaker.getRequest('/programs').subscribe(
      (response) => {
        this.dataSource = new MatTableDataSource(response);
        this.dataSource.sort = this.sort;
      },
      (error) => {
        console.error(error);
      }
    );
  }
}
