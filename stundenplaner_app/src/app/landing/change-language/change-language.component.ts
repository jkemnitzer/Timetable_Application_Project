import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
@Component({
  selector: 'app-change-language',
  templateUrl: './change-language.component.html',
  styleUrls: ['./change-language.component.scss']
})
export class ChangeLanguageComponent{
 
  constructor(public translate:TranslateService) { 
  }

  switchLang(event:any) {
    this.translate.use(event.value);
}
}
