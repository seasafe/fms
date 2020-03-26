import { Component, OnInit } from '@angular/core';
import { AuthService } from '../common/auth.service';
import { Constants } from '../common/constants';
import { User } from '../model/user.model';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NotificationService } from '../common/notification/notification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  submitted = false;
  returnUrl: string;

  constructor(private auth: AuthService, private router: Router, private route: ActivatedRoute,
    private fb: FormBuilder, private notificationService: NotificationService) { }

  ngOnInit(): void {

    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
    const currentUser = this.auth.loggedInUser;
    console.log('login component ', this.returnUrl, ' user = ', currentUser);
    if (currentUser) {
      this.router.navigate([this.returnUrl]);
      return;
    }
    this.loginForm = this.fb.group(
      {
        username: ['', Validators.required],
        password: ['', [Validators.required, Validators.minLength(5)]],
      }
    );
  }

  get loginFormValues() {
    return this.loginForm.controls;
  }
  login() {
    this.submitted = true;
    if (this.loginForm.invalid) {
      return;
    }

    this.auth.login(Constants.LOGIN_URL, this.loginFormValues.username.value, this.loginFormValues.password.value).subscribe(
      (user) => {
        console.log('user = ', user, ' is admin - ', this.auth.isAdmin);
        this.notificationService.success('Login Successfull',
          Constants.NOTIFICATION_STIKY_AUTOCLOSE_OPTIONS);



        // if (this.auth.isAdmin) {
        this.router.navigate(['/home']);
        // }
      });
  }

}
