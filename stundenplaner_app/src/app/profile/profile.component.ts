import { Component, OnInit } from '@angular/core';
import { HttpService } from "../http/http.service";
import { Profile } from "./data/profile";
import { Roles } from "./data/roles";
import { UserService } from "../user-service/user.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['../global.css','./profile.component.css']
})
export class ProfileComponent implements OnInit {

  users!: Profile[];
  user: Profile = {id: -1, username: "", email: "", status: "", roles: []};

  constructor(private requestMaker: HttpService, private userService: UserService) { }

  ngOnInit(): void {
    this.getUsers();
  }

  /*getUser(user: Profile){
    this.requestMaker.getRequest(`/users/${user.id}`).subscribe(
      (response) => {
        this.user = response;
      },
      (error) => {
        console.error(error);
      }
    );
  }*/
  private getUsers(){
    this.requestMaker.getRequest('/users').subscribe(
      (response) => {
        this.users = response;
        for(let user of this.users){
          if(user.username == this.userService.username){
            this.user = user;
          }
        }
      },
      (error) => {
        console.error(error);
      }
    );
  }

}
