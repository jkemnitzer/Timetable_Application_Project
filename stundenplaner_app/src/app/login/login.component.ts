import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatMenuTrigger} from '@angular/material/menu';
import {UserService} from '../user-service/user.service';
import {RegistrationComponent} from '../registration/registration.component';
import {CookieService} from "ngx-cookie-service";
import {HttpService} from "../http/http.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../global.css', './login.component.css'],
  providers: [RegistrationComponent]
})
export class LoginComponent implements OnInit {

  @ViewChild("trigger") menuTrigger!: MatMenuTrigger;
  @ViewChild("usernameInput") usernameInput!: ElementRef;

  constructor(
    public userService: UserService,
    private cookieService: CookieService,
    private httpService: HttpService,
    private registrationDialog: RegistrationComponent
  ) {
  }

  ngOnInit(): void {
    if (this.cookieService.check("token")) {
      this.httpService.headers = this.httpService.headers.set("Authorization", this.cookieService.get("token"))
    }
  }

  loginUser() {
    let username = this.usernameInput.nativeElement.value;
    if (username != "") {
      this.httpService.postRequest("/authenticate/login", {username: username, password: ""}).subscribe(
        (token) => {
          this.menuTrigger.closeMenu();
          this.cookieService.set("token", token.token);
          this.cookieService.set("username", username);
          this.httpService.headers = this.httpService.headers.set("Authorization", token.token);
          this.userService.username = username;
        }
      );
    }
  }

  openRegistrationDialog() {
    this.registrationDialog.openRegistrationDialog();
  }

  logoutUser() {
    this.httpService.deleteRequest("/authenticate").subscribe(
      () => {
        this.cookieService.delete("username");
        this.cookieService.delete("token");
        this.userService.username = this.userService.ANONYMOUS;
        this.httpService.headers = this.httpService.headers.delete("Authorization")
      }
    )
  }
}
