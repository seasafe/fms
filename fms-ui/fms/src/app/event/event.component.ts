import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../common/auth.service';
import { BasehttpService } from '../common/basehttp.service';
import { Constants } from '../common/constants';
import { Response } from '../model/response.model';
import { Event } from '../model/event.model';
import { NotificationService } from '../common/notification/notification.service';
import { Router } from '@angular/router';
import { LazyLoadEvent } from 'primeng/api/public_api';



@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss']
})
export class EventComponent implements OnInit {

  events: Event[];
  cols: any[];
  loading: boolean;
  totalRecords: number;
  @ViewChild('dt') eventsTable: any;
  constructor(private auth: AuthService, private http: BasehttpService, private notify: NotificationService, private router: Router) {
    this.http.get(Constants.GET_EVENTS_URL, null).subscribe((data: Response<Event>) => {
      this.events = data.content;
      this.totalRecords = data.totalElements;
    });
  }

  ngOnInit(): void {
    const employee = this.auth.loggedInUser;
    this.cols = [
      { field: '', header: 'Action' },
      { field: 'eventRefId', header: 'Event ID' },
      { field: 'eventDate', header: 'Month' },
      { field: 'baseLocation', header: 'Base Location' },
      { field: 'council', header: 'Council name' },
      { field: 'beneficiary', header: 'Beneficiary Name' },
      { field: 'eventName', header: 'Event Name' },
      { field: 'eventDate', header: 'Event Date' },
      { field: 'eventDate', header: 'Date' },
      { field: 'businessUnit', header: 'Business Unit' },
      { field: 'status', header: 'Status' },
      { field: 'venueAddress', header: 'Venue Address' },
      { field: 'totalVolunteers', header: 'Total Volunteers' },
      { field: 'totalVolunteerHours', header: 'Total Volunteer hours' },
      { field: 'totalTravelHours', header: 'Total travel hours' }
    ];
  }


  emailEventsFBReq() {
    const eventIds = this.events.map(e => e.eventId);
    console.log('eventIds - ' + eventIds);
    this.notify.success('Sending email for Feedback Request', Constants.NOTIFICATION_AUTOCLOSE_OPTIONS);
    this.http.post(Constants.SEND_EVENT_FEEDBACK_REQUEST_URL, eventIds).subscribe((data: any) => {
      this.notify.success('Emails to the participants successfully sent', Constants.NOTIFICATION_AUTOCLOSE_OPTIONS);
    });
  }

  editEvent(event: any) {
    console.log('edit Event called', event);
  }
  clearFilters(): void {
    this.eventsTable.reset();
  }

  loadLazy(event: LazyLoadEvent) {
    // lazy loading functionality
  }

}
