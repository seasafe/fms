import { Component, OnInit } from '@angular/core';
import { NgxUiLoaderService } from 'ngx-ui-loader';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  constructor(private loader: NgxUiLoaderService) {

  }
  title = 'fms';
  ngOnInit(): void {
    this.loader.start();
    setTimeout(() => {
      this.loader.stop();
    }, 500);
  }

}
