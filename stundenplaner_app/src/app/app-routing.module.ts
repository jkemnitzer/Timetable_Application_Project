import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProgramOverviewComponent } from './programs/program-overview/program-overview.component';
import { LecturesOverviewComponent } from './lectures/lectures-overview/lectures-overview.component';
import { RoomsComponent } from './rooms/rooms.component';
import { ProfileComponent } from "./profile/profile.component";
import { LandingComponent } from './landing/landing.component';
import { NavigationComponent } from './navigation/navigation.component';

const routes: Routes = [
  {path:'',component:LandingComponent},
  {path:'landing',component:LandingComponent},
  { path: 'programs', component: ProgramOverviewComponent },
  { path: 'lectures', component: LecturesOverviewComponent },
  { path: 'rooms', component: RoomsComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'navigation', component: NavigationComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
