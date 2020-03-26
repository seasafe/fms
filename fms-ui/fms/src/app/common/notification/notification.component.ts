import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { NotificationService } from './notification.service';
import { Notification, NotificationType } from './notification';
import { NavigationStart, Router } from '@angular/router';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit, OnDestroy {

  @Input() id = 'default-notification';
  @Input() fade = true;

  notifications: Notification[] = [];

  notificationSubscription: Subscription;
  routeSubscription: Subscription;

  constructor(private notificationService: NotificationService, private router: Router) { }
  ngOnDestroy(): void {
    this.notificationSubscription.unsubscribe();
    this.routeSubscription.unsubscribe();
  }

  ngOnInit(): void {
    console.log('In ngOnInit of Notification Component');
    this.notificationSubscription = this.notificationService.onNotification(this.id).subscribe(

      notification => {
        console.log('In ngOnInit of Notification Component - subscription method', notification);
        if (!notification.message) {
          this.notifications = this.notifications.filter(x => x.keepAfterRouteChange);
          this.notifications.forEach(x => delete x.keepAfterRouteChange);
          return;
        }


        this.notifications.push(notification);


        if (notification.autoClose) {
          setTimeout(() => this.removeNotification(notification), 3000);
        }
      }

    );

    this.routeSubscription = this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.notificationService.clear(this.id);
      }
    });

  }
  removeNotification(notification: Notification): void {
    if (!this.notifications.includes(notification)) {
      return;
    }

    if (this.fade) {
      this.notifications.find(x => x === notification).fade = true;
      setTimeout(() => {
        this.notifications = this.notifications.filter(x => x !== notification);
      }, 250);
    } else {

      this.notifications = this.notifications.filter(x => x !== notification);
    }
  }

  cssClass(notification: Notification) {
    if (!notification) {
      return;
    }
    const classes = ['alert', 'alert-dismissable'];

    const notificationTypeClasses = {
      [NotificationType.Success]: 'alert alert-success',
      [NotificationType.Error]: 'alert alert-danger',
      [NotificationType.Info]: 'alert alert-info',
      [NotificationType.Warning]: 'alert alert-warning'
    };

    classes.push(notificationTypeClasses[notification.type]);

    if (notification.fade) {
      classes.push('fade');
    }

    return classes.join(' ');
  }
}
