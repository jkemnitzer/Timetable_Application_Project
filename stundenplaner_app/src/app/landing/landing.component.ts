
import { TranslateService } from '@ngx-translate/core';
import { UserService } from "../user-service/user.service";
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {
  language !: string;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public userService: UserService,
    public translate: TranslateService
  ) {
  }

  ngOnInit(): void {
    this.translate.addLangs(['de', 'en']);
    this.translate.setDefaultLang('de');
    this.language = this.translate.getDefaultLang();
  }


  navigate(): void {
    this.router.navigate(['/programs'], {
      relativeTo: this.activatedRoute
    })
  }
  switchLang(lang: string) {
    this.translate.use(lang);
  }
}
