import {Semester} from "./semester";

export interface Program {
  id: number,
  name: string,
  semesters: Semester[]
}
