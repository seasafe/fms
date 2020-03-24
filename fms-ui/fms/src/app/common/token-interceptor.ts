import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { map, catchError } from 'rxjs/operators';
import { NotificationService } from './notification/notification.service';
import { Constants } from './constants';
@Injectable({ providedIn: 'root' })
export class TokenInterceptor implements HttpInterceptor {

    constructor(private authService: AuthService, private router: Router, private notificationService: NotificationService) { }

    getCustomHeaders(): HttpHeaders {
        const currentUser = this.authService.loggedInUser;
        const accessToken = this.authService.token;
        const headers = new HttpHeaders()
            .set('Content-Type', 'application/json')
            .set('Authorization', `Bearer ${accessToken}`)
            .set('EMP_ID', currentUser.username);
        return headers;
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const startTime = Date.now();
        const currentUser = this.authService.loggedInUser;
        const accessToken = this.authService.token;
        let request1: HttpRequest<any>;
        if (currentUser && accessToken) {

            request1 = request.clone({
                headers: new HttpHeaders({
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${accessToken}`,
                    EMP_ID: currentUser.username
                })
            });

            return next.handle(request1);
        } else {
            return next.handle(request);
        }
    }
}
