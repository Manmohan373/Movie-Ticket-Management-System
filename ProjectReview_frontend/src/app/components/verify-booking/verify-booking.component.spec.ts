import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VerifyBookingComponent } from './verify-booking.component';

describe('VerifyBookingComponent', () => {
  let component: VerifyBookingComponent;
  let fixture: ComponentFixture<VerifyBookingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VerifyBookingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VerifyBookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
