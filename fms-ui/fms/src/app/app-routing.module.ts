import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ReportsComponent } from './reports/reports.component';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';
import { EventComponent } from './event/event.component';
import { Authguard } from './common/authguard';
import { AuthService } from './common/auth.service';


const routes: Routes = [
  // {path: '', canActivate: [Authguard], children: [
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: AdminComponent, canActivate: [Authguard] },
  { path: 'home', component: HomeComponent, canActivate: [Authguard] },
  { path: 'events', component: EventComponent, canActivate: [Authguard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [Authguard] },
  { path: 'reports', component: ReportsComponent, canActivate: [Authguard] },
  { path: '', redirectTo: '/home', pathMatch: 'full' }
  // ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
