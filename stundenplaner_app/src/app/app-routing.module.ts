import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProgramOverviewComponent } from './programs/program-overview/program-overview.component';
import { LecturesOverviewComponent } from './lectures/lectures-overview/lectures-overview.component';
import { RoomsComponent } from './rooms/rooms.component';
import { ProfileComponent } from "./profile/profile.component";

const routes: Routes = [
  { path: 'programs', component: ProgramOverviewComponent },
  { path: 'lectures', component: LecturesOverviewComponent },
  { path: 'rooms', component: RoomsComponent },
  { path: 'profile', component: ProfileComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
