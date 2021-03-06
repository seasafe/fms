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
import { PmoComponent } from './pmo/pmo.component';
import { QuestionsComponent } from './admin/questions/questions.component';
import { FeedbackComponent } from './feedback/feedback.component';
import { FeedbacksuccessComponent } from './feedback/feedbacksuccess.component';
import { FeedbackalreadytakenComponent } from './feedback/feedbackalreadytaken.component';


const routes: Routes = [
  // {path: '', canActivate: [Authguard], children: [
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: AdminComponent, canActivate: [Authguard] },
  { path: 'question', component: QuestionsComponent, canActivate: [Authguard] },
  { path: 'question/:questionId', component: QuestionsComponent, canActivate: [Authguard] },
  { path: 'pmo', component: PmoComponent, canActivate: [Authguard] },
  { path: 'home', component: HomeComponent, canActivate: [Authguard] },
  { path: 'events', component: EventComponent, canActivate: [Authguard] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [Authguard] },
  { path: 'reports', component: ReportsComponent, canActivate: [Authguard] },
  { path: 'feedback', component: FeedbackComponent, canActivate: [Authguard] },
  { path: 'feedback-success', component: FeedbacksuccessComponent, canActivate: [Authguard] },
  { path: 'feedback-taken', component: FeedbackalreadytakenComponent, canActivate: [Authguard] },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', redirectTo: '/home', pathMatch: 'full' }
  // ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
