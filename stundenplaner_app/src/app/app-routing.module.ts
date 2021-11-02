import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProgramOverviewComponent} from "./programs/program-overview/program-overview.component";

const routes: Routes = [
  {path: 'programs', component: ProgramOverviewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
