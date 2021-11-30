import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public ANONYMOUS: string = "anonymous";
  private _loggedIn: boolean = false;
  private _username: string = this.ANONYMOUS;

  constructor() { }

  get username(): string {
    return this._username;
  }

  set username(value: string) {
    this._username = value;
    this._loggedIn = (this.username != this.ANONYMOUS)
  }

  get loggedIn(): boolean {
    return this._loggedIn;
  }
}
