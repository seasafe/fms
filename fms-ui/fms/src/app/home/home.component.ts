import { Component, OnInit } from '@angular/core';
import { AuthService } from '../common/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {
    if (this.auth.isAdmin) {
      this.router.navigate(['/dashboard']);
    } else {
      this.router.navigate(['/reports']);
    }
  }

}
