import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RoomsComponent } from './rooms/rooms.component';
import { ProfileComponent } from './profile/profile.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSliderModule } from '@angular/material/slider';
import { NavigationComponent } from './navigation/navigation.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ProgramOverviewComponent } from './programs/program-overview/program-overview.component';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSortModule } from '@angular/material/sort';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { EditProgramDialogComponent } from './programs/edit-program-dialog/edit-program-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { LoginComponent } from './login/login.component';
import { LecturesOverviewComponent } from './lectures/lectures-overview/lectures-overview.component';
import { EditLectureDialogComponent } from './lectures/edit-lecture-dialog/edit-lecture-dialog.component';
import { EditRoomDialogComponent } from './rooms/edit-room-dialog/edit-room-dialog.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { UsersComponent } from './users/users.component';
import { EditUsersDialogComponent } from './users/edit-users-dialog/edit-users-dialog.component';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { LandingComponent } from './landing/landing.component';
import {ShowTimetableComponent} from './show-timetable/show-timetable.component';
import {TimetableDayComponent} from './show-timetable/timetable-day/timetable-day.component';
import {MatExpansionModule} from "@angular/material/expansion";
import {UserRoleOverviewComponent} from "./user-roles/user-role-overview/user-role-overview.component";
import {ShowPermissionsDialogComponent} from "./user-roles/show-permissions-dialog/show-permissions-dialog.component";
import {MatSelectModule} from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { ChangeLanguageComponent } from './landing/change-language/change-language.component';
import { THEME } from './theme';
import { BehaviorSubject, Subject } from 'rxjs';
import { ThemeType } from './types/theme-type';
@NgModule({
  declarations: [
    AppComponent,
    RoomsComponent,
    ProfileComponent,
    NavigationComponent,
    NavigationComponent,
    EditProgramDialogComponent,
    ShowTimetableComponent,
    TimetableDayComponent,
    EditRoomDialogComponent,
    NavigationComponent,
    LoginComponent,
    ProgramOverviewComponent,
    LecturesOverviewComponent,
    EditLectureDialogComponent,
    UsersComponent,
    EditUsersDialogComponent,
    LandingComponent,
    UserRoleOverviewComponent,
    ShowPermissionsDialogComponent,
    ChangeLanguageComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSliderModule,
    LayoutModule,
    FlexLayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatTableModule,
    MatTooltipModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatSortModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    HttpClientModule,
    MatDialogModule,
    FormsModule,
    FormsModule,
    MatExpansionModule,
    MatPaginatorModule,
    MatSelectModule,
    BrowserAnimationsModule,
    MatSlideToggleModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: httpTranslateLoader,
        deps: [HttpClient],
      },
    }),
  ],
  providers: [
    {
      provide: THEME,
      useFactory: () => {
        return new BehaviorSubject<ThemeType>('LIGHT');
      }
    }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

export function httpTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
