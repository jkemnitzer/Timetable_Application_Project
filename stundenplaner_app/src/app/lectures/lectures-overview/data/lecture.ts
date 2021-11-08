/**
 * Author(s): Jan Gaida
 * Package(s): #6077
 */

import {Lesson} from "./lesson";

/**
 * The lecture-data-object
 */
export interface Lecture {
  id: number;
  lectureName: string;
  lessons: Lesson[];
}
