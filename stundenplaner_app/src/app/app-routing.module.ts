import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProgramOverviewComponent } from './programs/program-overview/program-overview.component';
import { LecturesOverviewComponent } from './lectures/lectures-overview/lectures-overview.component';
import { RoomsComponent } from './rooms/rooms.component';
import { UsersComponent } from './users/users.component';
import { ProfileComponent } from './profile/profile.component';
import { LandingComponent } from './landing/landing.component';

const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'landing', component: LandingComponent },
  { path: 'programs', component: ProgramOverviewComponent },
  { path: 'lectures', component: LecturesOverviewComponent },
  { path: 'rooms', component: RoomsComponent },
  { path: 'users', component: UsersComponent },
  { path: 'profile', component: ProfileComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
