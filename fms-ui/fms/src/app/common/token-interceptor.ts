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
                    'X-EMP-ID': currentUser.username
                })
            });

            return next.handle(request1).pipe(

                catchError((error, caught) => {
                    if (error.status === 401) {
                        this.authService.logout();
                        this.router.navigate(['/login']);
                        this.notificationService.error('Un Authorized Access!!! {{error.message}}',
                            Constants.NOTIFICATION_STIKY_AUTOCLOSE_OPTIONS);
                        return of(error.message);
                    }
                    return of(error);
                }),
                map((event: HttpEvent<any>) => {
                    if (event instanceof HttpResponse) {
                        const endTime = Date.now();
                        const timeTaken = endTime - startTime;
                        console.log(`Request for ${request1.urlWithParams} took ${timeTaken} ms.`);
                    }
                    return event;
                }));
        } else {
            return next.handle(request);
        }
    }
}
