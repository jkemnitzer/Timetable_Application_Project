export interface Program {
  id: number;
  programName: string;
  activeStudents: number;
}

export const MOCK_PROGRAMS: Program[] = [
  { id: 1, programName: 'Medieninformatik', activeStudents: 100 },
  { id: 2, programName: 'Informatik', activeStudents: 200 },
];
