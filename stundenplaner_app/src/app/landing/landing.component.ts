import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { UserService } from '../user/user.service';
@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {

  constructor(private router:Router,
    private activatedRoute:ActivatedRoute,
    public userService: UserService,
    public translate: TranslateService) {
      translate.addLangs(['de', 'en']);
    translate.setDefaultLang('de');
     }

  ngOnInit(): void {
  }

  navigate():void{
    this.router.navigate(['/programs'],{
      relativeTo:this.activatedRoute
    })
  }
  switchLang(lang: string) {
    this.translate.use(lang);
  }
}
