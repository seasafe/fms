import { Component, OnInit } from '@angular/core';
import { AuthService } from '../common/auth.service';
import { BasehttpService } from '../common/basehttp.service';
import { Constants } from '../common/constants';
import { Response } from '../model/response.model';
import { Event } from '../model/event.model';



@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss']
})
export class EventComponent implements OnInit {

  events: Event[];
  constructor(private auth: AuthService, private http: BasehttpService) {
    console.log('In Constructor Method of Event component');
    this.http.get(Constants.GET_EVENTS_URL, null).subscribe((data: Response<Event>) => {
      this.events = data.content;
    });
  }

  ngOnInit(): void {
    console.log('In NgOnInit Method of Events component');
    const employee = this.auth.loggedInUser;
  }

}
