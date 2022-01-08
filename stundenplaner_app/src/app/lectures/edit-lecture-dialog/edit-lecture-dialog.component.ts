/**
 * Author(s): Jan Gaida
 * Package(s): #6077
 */

import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {Lecture} from "../lectures-overview/data/lecture";
import {Lesson} from "../lectures-overview/data/lesson";

/**
 * Defines the dialog-type
 */
export enum DialogType {
  CREATE,
  EDIT,
}

/**
 * Used data-object in the EditLectureDialogComponent
 */
export interface DialogLectureData {
  lecture: Lecture;
  dialogType: DialogType;
}

/**
 * The EditLectureDialogComponent is a dialog-component, presenting the user with a given lecture or a lecture to be created
 */
@Component({
  selector: 'app-edit-program-dialog',
  templateUrl: './edit-lecture-dialog.component.html',
  styleUrls: ['../../global.css','./edit-lecture-dialog.component.css'],
})
export class EditLectureDialogComponent implements OnInit {

  // a reference to the DialogType-enum to access in the html-file
  DialogType = DialogType;

  /**
   * Constructor
   *
   * @param dialogRef the dialog-object passed to self
   * @param data the data-object used in this component
   */
  constructor(
    public dialogRef: MatDialogRef<EditLectureDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogLectureData
  ) { }

  /** ALC **/

  ngOnInit(): void {}

  /** Functions **/

  /**
   *  Will cancel the dialog
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
  isDialogType(data: DialogLectureData, dialogType: DialogType): boolean {
    return data.dialogType == dialogType;
  }

  /**
   * Returns whether given lecture has lessons
   *
   * @param lecture the lecture to check
   */
  hasLessons(lecture: Lecture): boolean {
    return lecture.lessons.length > 0;
  }

  /**
   * Returns a formatted string based on given lecture
   *
   * @param lecture the lecture to format as a string for this view
   */
  strLessons(lecture: Lecture): string {
    console.log(lecture)
    return String(lecture.lessons.length) + ' Sublektionen verknüpft.'
  }
}
