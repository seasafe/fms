import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedbackalreadytakenComponent } from './feedbackalreadytaken.component';

describe('FeedbackalreadytakenComponent', () => {
  let component: FeedbackalreadytakenComponent;
  let fixture: ComponentFixture<FeedbackalreadytakenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FeedbackalreadytakenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedbackalreadytakenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
