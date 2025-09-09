import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { TicketBooking } from 'src/app/models/ticket-booking';
import { TicketBookingService } from 'src/app/services/ticket-booking.service';

@Component({
  selector: 'app-verify-booking',
  templateUrl: './verify-booking.component.html',
  styleUrls: ['./verify-booking.component.css']
})
export class VerifyBookingComponent implements OnInit {

  booking: TicketBooking =new TicketBooking();
  bookingId: any;
  customerName: any;
  phnNo: any;


  constructor(
    private toastrService: ToastrService,
    private route: ActivatedRoute,
    private bookingService: TicketBookingService,
    private router: Router
  ) {}
  ngOnInit(): void {
    
    if (localStorage.getItem('datasaved') == 'no') {
      this.router.navigate(['/booking-list']);
    } else {
      this.bookingId = this.route.snapshot.params['bookingId'];
      this.customerName = this.route.snapshot.params['customerName'];
      this.phnNo = this.route.snapshot.params['phnNo'];


      this.bookingService
        .getById(this.bookingId, this.customerName,this.phnNo)
        .subscribe(
          (data) => {
            if (data.code == 'DELETED') {
              this.toastrService.warning(data.message);
              this.goToBookingList();
            } else {
              this.booking = data.details[0];
            }
          },
          (error) => console.log(error)
        );
    }
  }

  goToBookingList() {
    this.router.navigate(['/booking-list']);
    }
    verifyBooking() {
      this.bookingService
      .verifyBooking(this.booking.bookingId, this.booking.customerName,this.booking.phnNo)
      .subscribe(
        (data) => {
          this.toastrService.info(data.details[0].bookingStatus);
          this.goToBookingList();
        },
        (error) => console.log(error)
      );    }

}
