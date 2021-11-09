import { Component, OnInit } from '@angular/core';
import { HttpService } from "../http/http.service";
import { Profile } from "./data/profile";
import { Roles } from "./data/roles";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  users!: Profile[];
  title = 'Profil';

  constructor(private requestMaker: HttpService) { }

  ngOnInit(): void {
    this.getUsers();
  }

  getUser(user: Profile){
    this.requestMaker.getRequest(`/users/${user.id}`).subscribe(
      (response) => {
        //TODO: wait for login management
      },
      (error) => {
        console.error(error);
      }
    );
  }
  private getUsers(){
    this.requestMaker.getRequest('/users').subscribe(
      (response) => {
        this.users = response;
      },
      (error) => {
        console.error(error);
      }
    );
  }

}
