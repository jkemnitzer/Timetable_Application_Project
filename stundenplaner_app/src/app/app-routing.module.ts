import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProgramOverviewComponent} from "./programs/program-overview/program-overview.component";
import {ShowTimetableComponent} from "./show-timetable/show-timetable.component";

const routes: Routes = [
  {path: 'programs', component: ProgramOverviewComponent},
  {path: 'show-timetable', component: ShowTimetableComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
