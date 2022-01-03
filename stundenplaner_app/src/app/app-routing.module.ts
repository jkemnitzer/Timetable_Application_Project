import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProgramOverviewComponent } from './programs/program-overview/program-overview.component';
import { LecturesOverviewComponent } from './lectures/lectures-overview/lectures-overview.component';
import { RoomsComponent } from './rooms/rooms.component';
import { UsersComponent } from './users/users.component';
import { ProfileComponent } from './profile/profile.component';
import { LandingComponent } from './landing/landing.component';
import { UserRoleOverviewComponent } from "./user-roles/user-role-overview/user-role-overview.component";
import { LoggedInUserGuard } from "./guards/logged-in-user/logged-in-user-guard";
import { GuardViewComponent } from "./guards/guard-view/guard-view.component";
import { UserRoleType } from "./user-roles/data/user-role-type";
import { PermissionGuard } from "./guards/permission/permission-guard";
import {ShowTimetableComponent} from "./show-timetable/show-timetable.component";

const routes: Routes = [
  {
    path: '',
    component: LandingComponent,
  },
  {
    path: 'landing',
    component: LandingComponent,
  },
  {
    path: 'programs',
    component: ProgramOverviewComponent,
    canActivate: [
      PermissionGuard,
    ],
    data: {
      allowed_user_roles: [
        UserRoleType.ADMIN,
        UserRoleType.STUDENT,
        UserRoleType.PROFESSOR,
        UserRoleType.LECTURER,
        UserRoleType.GUEST
      ]
    }
  },
  {
    path: 'lectures',
    component: LecturesOverviewComponent,
    canActivate: [
      LoggedInUserGuard,
      PermissionGuard,
    ],
    data: {
      allowed_user_roles: [
        UserRoleType.ADMIN,
        UserRoleType.PROFESSOR,
        UserRoleType.LECTURER,
      ]
    }
  },
  {
    path: 'rooms',
    component: RoomsComponent,
    canActivate: [
      LoggedInUserGuard,
      PermissionGuard,
    ],
    data: {
      allowed_user_roles: [
        UserRoleType.ADMIN,
        UserRoleType.PROFESSOR,
        UserRoleType.LECTURER,
      ]
    }
  },
  {
    path: 'users',
    component: UsersComponent,
    canActivate: [
      LoggedInUserGuard,
      PermissionGuard,
    ],
    data: {
      allowed_user_roles: [
        UserRoleType.ADMIN,
      ]
    }
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [
      LoggedInUserGuard,
      PermissionGuard,
    ],
    data: {
      allowed_user_roles: [
        UserRoleType.ADMIN,
        UserRoleType.STUDENT,
        UserRoleType.PROFESSOR,
        UserRoleType.LECTURER,
      ]
    }
  },
  {
    path: 'user-role',
    component: UserRoleOverviewComponent,
    canActivate: [
      LoggedInUserGuard,
      PermissionGuard,
    ],
    data: {
      allowed_user_roles: [
        UserRoleType.ADMIN,
      ]
    }
  },
  {
    path: 'guard',
    component: GuardViewComponent
  },
  {
    path: 'show-timetable',
    component: ShowTimetableComponent,
    canActivate: [
      LoggedInUserGuard,
      PermissionGuard,
    ],
    data: {
      allowed_user_roles: [
        UserRoleType.ADMIN,
        UserRoleType.STUDENT,
        UserRoleType.PROFESSOR,
        UserRoleType.LECTURER,
      ]
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
