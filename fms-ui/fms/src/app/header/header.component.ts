import { Component, OnInit } from '@angular/core';
import { AuthService } from '../common/auth.service';
import { LoggerService } from '../common/logger.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  username: string;
  isAdmin: boolean;
  isPMO: boolean;
  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.username = this.auth.loggedInUser?.username;
    this.isAdmin = this.auth.isAdmin;
    this.isPMO = this.auth.isPMO;
  }

  logout() {
    console.log('in logout');
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
