import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { User } from './data/user';
import { HttpService } from '../http/http.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent {
  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  public registrationForm!: FormGroup;
  constructor(
    public dialog: MatDialog,
    private requestMaker: HttpService,
    private formBuilder: FormBuilder
  ) {}

  openRegistrationDialog() {
    this.dialog.open(RegistrationComponent);
  }

  private registerUser(user: User) {
    this.requestMaker
      .postRequest('/register', this.registrationForm.value)
      .subscribe((res) => {
        alert('Registration Successfull');
      });
  }

  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      username: [''],
      email: [''],
      password: [''],
    });
  }
}
