export interface Room {
  id: number;
  number: string;
  building: string;
}

export const MOCK_ROOMS: Room[] = [
  { id: 1, number: 'B109', building: 'B' },
  { id: 2, number: 'G028/29', building: 'G' },
];
