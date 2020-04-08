import { Component, OnInit } from '@angular/core';
import { AuthService } from '../common/auth.service';
import { BasehttpService } from '../common/basehttp.service';
import { Constants } from '../common/constants';
import { Response } from '../model/response.model';
import { Event } from '../model/event.model';
import { NotificationService } from '../common/notification/notification.service';



@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss']
})
export class EventComponent implements OnInit {

  events: Event[];
  constructor(private auth: AuthService, private http: BasehttpService, private notify: NotificationService) {
    console.log('In Constructor Method of Event component');
    this.http.get(Constants.GET_EVENTS_URL, null).subscribe((data: Response<Event>) => {
      this.events = data.content;
    });
  }

  ngOnInit(): void {
    console.log('In NgOnInit Method of Events component');
    const employee = this.auth.loggedInUser;
  }


  emailEventsFBReq() {
    const eventIds = this.events.map(e => e.eventId);
    console.log('eventIds - ' + eventIds);
    this.notify.success('Sending email for Feedback Request', Constants.NOTIFICATION_AUTOCLOSE_OPTIONS);
    this.http.post(Constants.SEND_EVENT_FEEDBACK_REQUEST_URL, eventIds).subscribe((data: any) => {
      this.notify.success('Emails to the participants successfully sent', Constants.NOTIFICATION_AUTOCLOSE_OPTIONS);
    });
  }
}
