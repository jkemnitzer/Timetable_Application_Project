/**
 * Author(s): Jan Gaida
 * Package(s): #6234
 */

import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {GuardReason} from "../data/guard-reason";
import {UserService} from "../../user-service/user.service";

@Injectable()
export class LoggedInUserGuard implements CanActivate {

  /**
   * Constructor
   *
   * @param userService the user-service injected to this
   * @param router the reference to the angular-router-object
   */
  constructor(
    private userService: UserService,
    private router: Router
  ) {};

  /**
   * Interface-implantation
   *
   * @param route the route that is about to be triggered
   * @param state the state of the router
   */
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean|UrlTree>|Promise<boolean|UrlTree>|boolean|UrlTree {
    try {
      if (this.userService.loggedIn) {
        return true;
      } else {
        // when the user is not logged in we delegate to the guard-view
        let _ = this.router.navigate(['guard', { reason: GuardReason.MUST_BE_LOGGED_IN }]);
        return false;
      }
    } catch (error) {
      let _ = this.router.navigate(['guard', { reason: GuardReason.UNKNOWN }]);
      return false;
    }
  }
}
