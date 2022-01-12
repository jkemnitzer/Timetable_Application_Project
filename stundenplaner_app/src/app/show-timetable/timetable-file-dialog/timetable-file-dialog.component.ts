/**
 * Author(s): Jan Gaida
 * Package(s): #6314, #6319
 */

import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {HttpService} from "../../http/http.service";

/**
 * Defines the dialog-type
 */
export enum DialogType {
  FILE_UPLOAD,
  FILE_UPLOADING,
  FILE_UPLOADED,
  FILE_UPLOAD_FAILED
}

/**
 * Used data-object in the TimetableFileDialogComponent
 */
export interface DialogTimetableFileData {
  dialogType: DialogType;
}

@Component({
  selector: 'app-timetable-file-dialog',
  templateUrl: './timetable-file-dialog.component.html',
  styleUrls: ['./timetable-file-dialog.component.css']
})
export class TimetableFileDialogComponent implements OnInit {

  // a reference to the DialogType-enum to access in the html-file
  DialogType = DialogType;
  // either the uploaded file or null
  fileUploadTimetable: File | null = null;
  // the accept-property of the input-filed responsible for the file-upload
  acceptedFileUploadType: string = ".xls";
  // the
  import_timetable_path: string = "/timetable/import"

  /**
   * Constructor
   *
   * @param httpService the HttpService to upload data with
   * @param dialogRef the Dialog which will be opened/closed upon certain user-actions
   * @param data the Dialogs-Data-Object
   * @param dialog the Dialog which will be opened/closed upon certain user-actions
   */
  constructor(
    private httpService: HttpService,
    public dialogRef: MatDialogRef<TimetableFileDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogTimetableFileData,
    public dialog: MatDialog
  ) {
  }

  /** ALC **/

  ngOnInit(): void {
  }

  /** Functions **/

  /**
   *  Will cause the dialog to close
   */
  cancelDialog(): void {
    this.dialogRef.close();
  }

  /**
   * Returns whether the given data-object is of given dialogType
   *
   * @param data the data-object to check
   * @param dialogType the targeted dialogType of this check
   */
  isDialogType(data: DialogTimetableFileData, dialogType: DialogType): boolean {
    return data.dialogType == dialogType;
  }

  /**
   * Will initially handle the user-determined file
   *
   * @param event the File-Input-Button-Event caused by the user
   */
  handleFileInput(event: Event): void {
    try {
      // @ts-ignore, if failure will be handled by catch
      this.fileUploadTimetable = event.target.files.item(0);
    } catch (error) {
      this.fileUploadTimetable = null;
    }
  }

  /**
   * Will handle the importing of the (ideally already) determined timetable-file by uploading it to the API
   */
  uploadTimetable() {
    // only proceed if there is actually a file available
    if (!(this.fileUploadTimetable == null)) {
      // show FILE_UPLOADING
      this.dialogRef.close();
      this.dialogRef = this.dialog.open(
        TimetableFileDialogComponent, {
          data: {dialogType: DialogType.FILE_UPLOADING}
        }
      );
      this.dialogRef.afterOpened().subscribe(_ => {
        let upload_headers = new Headers();
        upload_headers.append('Content-Type', 'multipart/form-data');
        // post form-data
        let form_data = new FormData();
        form_data.append('file', <File>this.fileUploadTimetable)
        this.httpService.postRequest(this.import_timetable_path, form_data).subscribe(
          _ => {
            this.dialogRef.close();
            this.dialogRef = this.dialog.open(
              TimetableFileDialogComponent, {
                data: {dialogType: DialogType.FILE_UPLOADED}
              }
            );
          }, (_) => {
            this.fileUploadTimetable = null;
            this.dialogRef.close();
            this.dialogRef = this.dialog.open(
              TimetableFileDialogComponent, {
                data: {dialogType: DialogType.FILE_UPLOAD_FAILED}
              }
            );
          }
        )
      }, (_) => {
        // should not be triggered:
        this.fileUploadTimetable = null;
        this.dialogRef.close();
      });
    }
  }
}
