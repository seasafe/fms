import { Component, OnInit } from '@angular/core';
import { AuthService } from '../common/auth.service';
import { BasehttpService } from '../common/basehttp.service';
import { Constants } from '../common/constants';
import { map } from 'rxjs/operators';
import { Response } from '../model/response.model';
import { Event } from '../model/event.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  totalParticipants = 0;
  totalVolunteers = 0;
  livesImpacted = 0;
  totalEvents = 0;
  constructor(private auth: AuthService, private http: BasehttpService) {
    console.log('In Constructor Method of Dashboard component');
    this.http.get(Constants.GET_EVENTS_URL, null).subscribe((data: Response<Event>) => {
      const events = data.content;
      this.totalParticipants = events.reduce((acc, event) => acc + event.totalVolunteers, 0);
      this.totalVolunteers = events.reduce((acc, event) => acc + event.totalVolunteers, 0);
      this.livesImpacted = events.reduce((acc, event) => acc + event.liveImpacted, 0);
      this.totalEvents = events.reduce((acc, event) => acc + 1, 0);

    });
  }

  ngOnInit(): void {
    console.log('In NgOnInit Method of Dashboard component');
    const employee = this.auth.loggedInUser;


  }

}
