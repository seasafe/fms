import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
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
import { NgxUiLoaderModule, NgxUiLoaderHttpModule, NgxUiLoaderConfig } from 'ngx-ui-loader';
import { PmoComponent } from './pmo/pmo.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
/* Angular material 8 */
import { AngularMaterialModule } from './angular-material.module';
import { TableModule } from 'primeng/table';
import { QuestionsComponent } from './admin/questions/questions.component';
import { FeedbackComponent } from './feedback/feedback.component';
import { FeedbacksuccessComponent } from './feedback/feedbacksuccess.component';
import { FeedbackalreadytakenComponent } from './feedback/feedbackalreadytaken.component';

const ngxUiLoaderConfig: NgxUiLoaderConfig = {
  bgsColor: '#1036A0',
  bgsOpacity: 0.5,
  bgsPosition: 'bottom-right',
  bgsSize: 60,
  bgsType: 'ball-spin-clockwise-fade-rotating',
  blur: 10,
  delay: 0,
  fastFadeOut: true,
  fgsColor: '#1036a0',
  fgsPosition: 'center-center',
  fgsSize: 100,
  fgsType: 'three-strings',
  gap: 24,
  logoPosition: 'center-center',
  logoSize: 120,
  logoUrl: 'assets/cognizant.png',
  masterLoaderId: 'master',
  overlayBorderRadius: '0',
  overlayColor: 'rgb(224,224,224)',
  pbColor: '#0f359e',
  pbDirection: 'ltr',
  pbThickness: 3,
  hasProgressBar: true,
  text: 'Loading....',
  textColor: '#0f359e',
  textPosition: 'center-center',
  maxTime: -1,
  minTime: 300
};

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
    AdminComponent,
    PmoComponent,
    QuestionsComponent,
    FeedbackComponent,
    FeedbacksuccessComponent,
    FeedbackalreadytakenComponent
  ],
  imports: [BrowserModule, AppRoutingModule, NgbModule, FormsModule,
    ReactiveFormsModule, MDBBootstrapModule.forRoot(), NotificationModule, HttpClientModule,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig), NgxUiLoaderHttpModule.forRoot({ showForeground: true }), BrowserAnimationsModule,
    // AngularMaterialModule,
    TableModule],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true, deps: [AuthService, Router] },
  { provide: Authguard }],
  bootstrap: [AppComponent]
})
export class AppModule { }
