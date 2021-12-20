import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
@Component({
  selector: 'app-change-language',
  templateUrl: './change-language.component.html',
  styleUrls: ['./change-language.component.scss']
})
export class ChangeLanguageComponent {

  language: string = '';
  constructor(public translate: TranslateService) {
  }

  ngOnInit() {
    this.translate.addLangs(['de', 'en']);
    this.translate.setDefaultLang('de');
    this.language = this.translate.getDefaultLang();
  }

  switchLang(lang: string) {
    this.translate.use(lang);
  }
}
