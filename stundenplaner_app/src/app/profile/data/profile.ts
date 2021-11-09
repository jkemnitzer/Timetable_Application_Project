import {Roles} from './roles';

export interface Profile {
  id: number
  username: string
  email: string
  status: string
  roles: Roles[]
}
