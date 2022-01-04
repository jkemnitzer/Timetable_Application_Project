import {Injectable} from '@angular/core';
import {UserRoleType} from "../user-roles/data/user-role-type";

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

  /** Functions **/

  /**
   * Checks whether the current set user is of at least one of given user-role-type
   *
   * @param roleTypes array of user-role-types to check against
   *
   * @return boolean whether the given role-types matches at least one of the role-types of self
   */
  userHasRoleType(roleTypes: Array<number>): boolean {
    // check if guest:
    if (this.username == this.ANONYMOUS) {
      for (let i in roleTypes) {
        if (UserRoleType[UserRoleType.GUEST] == UserRoleType[roleTypes[i]]) {
          return true
        }
      }
      // skip further checks, since GUEST is not in roleTypes, while the username indicates to be guest.
      return false;
    }
    // check non-guest
    // todo: this is a workaround since this user-service doesn't actually contain the backend-user-object.
    let username = this._username.toLowerCase().trim();
    for (let i in roleTypes) {
      let roleStr = UserRoleType[roleTypes[i]].toLowerCase().trim();
      if (username == roleStr) {
        return true;
      }
    }
    // if no role-types are matched the permission shall not be granted.
    return false;
  }
}
