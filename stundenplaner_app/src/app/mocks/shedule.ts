export interface Lesson {
  name: string;
  weekday: number;
  start: number;
  length: number;
  lecturer: string;
  room: string;
}

export const MOCK_SHEDULE: Lesson[] = [
  {name: 'Betriebsysteme', weekday: 0, start:25200000, length: 90, lecturer: 'Michael Stepping', room: 'A010'},
  {name: 'Betriebsysteme', weekday: 0, start:19000000, length: 90, lecturer: 'Michael Stepping', room: 'A010'},

];
