import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { FormControl, Validators } from '@angular/forms';

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
  constructor(public dialog: MatDialog) {}

  openRegistrationDialog() {
    this.dialog.open(RegistrationComponent);
  }

  ngOnInit(): void {}
}
