import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatMenuTrigger} from "@angular/material/menu";
import {UserService} from "../user/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @ViewChild("trigger") menuTrigger!: MatMenuTrigger;
  @ViewChild("usernameInput") usernameInput!: ElementRef;

  constructor(public userService: UserService) {
  }

  ngOnInit(): void {
  }

  loginUser() {
    let username = this.usernameInput.nativeElement.value;
    if (username != "") {
      this.menuTrigger.closeMenu();
      this.userService.username = username
    }
  }

  logoutUser() {
    this.userService.username = this.userService.ANONYMOUS;
  }

}
