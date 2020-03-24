import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ReportsComponent } from './reports/reports.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TokenInterceptor } from './common/token-interceptor';
import { AuthService } from './common/auth.service';
import { Router } from '@angular/router';
import { EventComponent } from './event/event.component';
import { AdminComponent } from './admin/admin.component';
import { Authguard } from './common/authguard';
import { NotificationModule } from './common/notification/notification.module';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    HomeComponent,
    DashboardComponent,
    ReportsComponent,
    EventComponent,
    AdminComponent
  ],

  imports: [BrowserModule, AppRoutingModule, NgbModule, FormsModule,
    ReactiveFormsModule, MDBBootstrapModule.forRoot(), NotificationModule, HttpClientModule],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true, deps: [AuthService, Router] },
  { provide: Authguard }],
  bootstrap: [AppComponent]
})
export class AppModule { }
