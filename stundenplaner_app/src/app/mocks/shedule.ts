export interface Lesson {
  name: string;
  weekday: number;
  startHour: number;
  startMinutes: number;
  length: number;
  lecturer: string;
  room: string;
}

export const MOCK_SHEDULE: Lesson[] = [
  {name: 'Betriebsysteme', weekday: 0, startHour: 10,startMinutes: 30, length: 90, lecturer: 'Michael Stepping', room: 'A010'},
  {name: 'Betriebsysteme', weekday: 0, startHour: 7,startMinutes: 30, length: 90, lecturer: 'Michael Stepping', room: 'A010'},

];
