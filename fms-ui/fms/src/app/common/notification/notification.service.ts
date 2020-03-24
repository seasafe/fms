import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { filter } from 'rxjs/operators';
import { Notification, NotificationType } from './notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private subject = new Subject<Notification>();
  private defaultId = 'default-notification';
  constructor() { }

  onNotification(id = this.defaultId): Observable<Notification> {
    return this.subject.asObservable().pipe(filter(x => x && x.id === id));
  }
  success(message: string, options?: any) {
    this.notification(new Notification({ ...options, type: NotificationType.Success, message }));
  }

  error(message: string, options?: any) {
    this.notification(new Notification({ ...options, type: NotificationType.Error, message }));
  }

  info(message: string, options?: any) {
    this.notification(new Notification({ ...options, type: NotificationType.Info, message }));
  }

  warn(message: string, options?: any) {
    this.notification(new Notification({ ...options, type: NotificationType.Warning, message }));
  }

  notification(notification: Notification) {
    notification.id = notification.id || this.defaultId;
    this.subject.next(notification);
  }

  clear(id = this.defaultId) {
    this.subject.next(new Notification({ id }));
  }
}
