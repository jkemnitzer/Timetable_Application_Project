import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProgramOverviewComponent} from './programs/program-overview/program-overview.component';
import {LecturesOverviewComponent} from './lectures/lectures-overview/lectures-overview.component';
import {RoomsComponent} from './rooms/rooms.component';
import {UsersComponent} from './users/users.component';
import {ProfileComponent} from './profile/profile.component';
import {LandingComponent} from './landing/landing.component';
import {ShowTimetableComponent} from "./show-timetable/show-timetable.component";
import {UserRoleOverviewComponent} from "./user-roles/user-role-overview/user-role-overview.component";

const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'programs', component: ProgramOverviewComponent },
  { path: 'lectures', component: LecturesOverviewComponent },
  { path: 'rooms', component: RoomsComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user-role', component: UserRoleOverviewComponent },
  { path: 'show-timetable', component: ShowTimetableComponent},
  { path: 'users', component: UsersComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
