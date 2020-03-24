import { Injectable } from '@angular/core';
import { User, Authorities } from '../model/user.model';
import { Observable, BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loggedUser: BehaviorSubject<User>;
  private currentUser: Observable<User>;
  user = new User();
  constructor(private http: HttpClient) {
    this.loggedUser = new BehaviorSubject<User>(JSON.parse(sessionStorage.getItem('loggedUser')));
    this.currentUser = this.loggedUser.asObservable();
  }

  login(url: string, userName: string, password: string) {
    const userRequest = new User();
    userRequest.username = userName;
    userRequest.password = password;

    return this.http.post<any>(url, userRequest)
      .pipe(
        map(loginResponse => {
          if (loginResponse && loginResponse.user) {
            const user = loginResponse.user;
            sessionStorage.setItem('loggedUser', JSON.stringify(user));
            this.loggedUser.next(user);
            sessionStorage.setItem('token', loginResponse.token);
            this.user = user;

          }
          return this.user;
        }));
  }

  logout() {
    sessionStorage.removeItem('loggedUser');
    sessionStorage.removeItem('token');
    this.loggedUser.next(null);
  }

  public get token(): string {
    return sessionStorage.getItem('token');
  }

  public get loggedInUser(): User {
    return this.loggedUser.value;
  }

  public get isAdmin(): boolean {
    let isAdmin = false;
    if (this.loggedInUser) {
      this.loggedInUser.authorities.forEach(role => {
        if (role.authority === 'ROLE_ADMIN') {
          isAdmin = true;
        }
      });
    }
    return isAdmin;
  }

  public get isPMO(): boolean {
    let isPMO = false;
    if (this.loggedInUser) {
      this.loggedInUser.authorities.forEach(role => {
        if (role.authority === 'ROLE_PMO') {
          isPMO = true;
        }
      });
    }
    return isPMO;
  }
}
