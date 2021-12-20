import {Router} from '@angular/router';
import { ChangeDetectorRef, Component, Inject, OnInit } from '@angular/core';
import { THEME } from './theme';
import { BehaviorSubject, Subject } from 'rxjs';
import { ThemeType } from './types/theme-type';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})

export class AppComponent{

  title = 'Stunden- und Raumplaner-App';
  constructor(public router: Router,
    @Inject(THEME) public themeSubject: BehaviorSubject<ThemeType>) {
    this.updateTheme();
  }

    updateTheme() {
      const savedTheme: ThemeType = (localStorage.getItem('theme') as ThemeType);
      if (savedTheme) {
        this.themeSubject.next(savedTheme);
      }
    }

}
