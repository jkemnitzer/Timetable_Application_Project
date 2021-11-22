import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatMenuTrigger } from '@angular/material/menu';
import { UserService } from '../user/user.service';
import { RegistrationComponent } from '../registration/registration.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [RegistrationComponent],
})
export class LoginComponent implements OnInit {
  @ViewChild('trigger') menuTrigger!: MatMenuTrigger;
  @ViewChild('usernameInput') usernameInput!: ElementRef;

  constructor(
    public userService: UserService,
    private registrationDialog: RegistrationComponent
  ) {}

  ngOnInit(): void {}

  loginUser() {
    let username = this.usernameInput.nativeElement.value;
    if (username != '') {
      this.menuTrigger.closeMenu();
      this.userService.username = username;
    }
  }

  openRegistrationDialog() {
    this.registrationDialog.openRegistrationDialog();
  }

  logoutUser() {
    this.userService.username = this.userService.ANONYMOUS;
  }
}
