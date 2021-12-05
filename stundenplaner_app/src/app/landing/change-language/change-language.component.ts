import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MatThemeSelectChangeInterface } from 'src/app/interfaces/mat-theme-select-change.interface';

@Component({
  selector: 'app-change-language',
  templateUrl: './change-language.component.html',
  styleUrls: ['./change-language.component.scss']
})
export class ChangeLanguageComponent{
 
  constructor(public translate:TranslateService) { 
  }

  switchLang(event:MatThemeSelectChangeInterface) {
    this.translate.use(event.value);
}
}
