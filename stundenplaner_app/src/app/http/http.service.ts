import {HttpClient, HttpErrorResponse, HttpHeaders,} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {Router} from "@angular/router";
import {GuardViewComponent} from "../guards/guard-view/guard-view.component";
import {GuardReason} from "../guards/data/guard-reason";

@Injectable({
  providedIn: 'root',
})
export class HttpService {
  url = environment.api;
  headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient, private router: Router) {
  }

  getRequest(path: string, options?: any): Observable<any> {
    return this.http
      .get(`${this.url}${path}`, options)
      .pipe(catchError(this.handleError));
  }

  postRequest(path: string, data: any, options?: any): Observable<any> {
    return this.http
      .post(`${this.url}${path}`, data, options)
      .pipe(catchError(this.handleError));
  }

  updateRequest(path: string, data: any, options?: any): Observable<any> {
    return this.http
      .put(`${this.url}${path}`, data, options)
      .pipe(catchError(this.handleError));
  }

  deleteRequest(path: string, options?: any): Observable<any> {
    return this.http
      .delete(`${this.url}${path}`, options)
      .pipe(catchError(this.handleError));
  }

  handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else if (error.status === 401) { // Unauthorized
      let _ = this.router.navigate(['guard', {reason: GuardReason.UNAUTHORIZED_ACCESS}] )
    } else if (error.status === 403) { // Forbidden
      let _ = this.router.navigate(['guard', {reason: GuardReason.FORBIDDEN_ACCESS}] )
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `,
        error.error
      );
    }
    // Return an observable with a user-facing error message.
    return throwError('Something bad happened; please try again later.');
  }
}
