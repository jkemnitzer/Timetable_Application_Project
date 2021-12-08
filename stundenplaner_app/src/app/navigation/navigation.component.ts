import {Component} from '@angular/core';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {Observable} from 'rxjs';
import {map, shareReplay} from 'rxjs/operators';
import {UserService} from "../users/user.service";
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css'],
})
export class NavigationComponent {
  isHandset$: Observable<boolean> = this.breakpointObserver
    .observe(Breakpoints.Handset)
    .pipe(
      map((result) => result.matches),
      shareReplay()
    );

    switchLang(lang: string) {
      this.translate.use(lang);
    }

  constructor(private breakpointObserver: BreakpointObserver, public userService: UserService, public translate: TranslateService) {
    translate.addLangs(['de', 'en']);
    translate.setDefaultLang('de');
  }
}
