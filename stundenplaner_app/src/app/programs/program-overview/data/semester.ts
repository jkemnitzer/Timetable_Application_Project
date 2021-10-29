export interface Semester {
  id: bigint,
  number: string,
  expectedParticipants: number,
  actualParticipants: number,
  programId: bigint
}
