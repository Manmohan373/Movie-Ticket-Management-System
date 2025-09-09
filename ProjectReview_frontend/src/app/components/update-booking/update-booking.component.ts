import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ToastrService } from 'ngx-toastr';
import { TicketBooking } from 'src/app/models/ticket-booking';
import { TicketBookingService } from 'src/app/services/ticket-booking.service';

@Component({
  selector: 'app-update-booking',
  templateUrl: './update-booking.component.html',
  styleUrls: ['./update-booking.component.css'],
})
export class UpdateBookingComponent implements OnInit {
  timeSlots: Array<any>;

  booking: TicketBooking = new TicketBooking();
  validPrice: any;
  validBookingDate: any;
  validMovieName: any;
  validEmail: any;
  validPhnNo: any;
  validAge: any;
  validSeats: any;
  validCustomerName: any;
  validTimeSlot: any;
  validBookingId: any;
  bookingId: any;
  customerName: any;
  phnNo: any;

  constructor(
    private router: Router,
    private toastrService: ToastrService,
    private bookingService: TicketBookingService,
    private jwtservice: JwtHelperService,
    private route: ActivatedRoute
  ) {}
  ngOnInit(): void {
    if (localStorage.getItem('datasaved') == 'no') {
      this.router.navigate(['/booking-list']);
    } else {
      this.bookingId = this.route.snapshot.params['bookingId'];
      this.customerName = this.route.snapshot.params['customerName'];
      this.phnNo = this.route.snapshot.params['phnNo'];

      this.bookingService
        .getById(this.bookingId, this.customerName, this.phnNo)
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
    this.bookingService.getTimeSlot().subscribe((data) => {
      console.log(data);
      this.timeSlots = data.details[0].timeSlots;
    });
  }
  onSubmit() {
    if (this.jwtservice.isTokenExpired(localStorage.getItem('access_token'))) {
      this.toastrService.error('Token Expired!!!!!');
      this.router.navigate(['/login-page']);
    } else {
      this.bookTicket();
    }
  }
  bookTicket() {
    this.bookingService
      .updateTicket(this.bookingId, this.customerName, this.phnNo, this.booking)
      .subscribe(
        (data) => {
          if (data.code == 'Record Not Found') {
            this.validBookingId = data.message;
            this.validCustomerName = data.message;
            this.validPhnNo = data.message;
          } else if (data.code == 'VALERRCOD') {
            data.details.forEach((element) => {
              const keys = Object.keys(element);
              const key = keys[0];
              const value = element[key];
              switch (key) {
                case 'bookingId':
                  this.validBookingId = value;
                  break;
                case 'customerName':
                  this.validCustomerName = value;
                  break;
                case 'timeSlot':
                  this.validTimeSlot = value;
                  break;
                case 'email':
                  this.validEmail = value;
                  break;
                case 'seats':
                  this.validSeats = value;
                  break;
                case 'movieName':
                  this.validMovieName = value;
                  break;
                case 'age':
                  this.validAge = value;
                  break;
                case 'phnNo':
                  this.validPhnNo = value;
                  break;
                case 'bookingDate':
                  this.validBookingDate = value;
                  break;
                case 'price':
                  this.validPrice = value;
                  break;
                default:
                  break;
              }
              
            });
          } else {
            this.toastrService.success('Account created succesfully');
            this.goToBookingList();
          }
        },
        (error) => console.log(error)
      );
  }
  onCourseSelect(event: any) {
    this.booking.timeSlot = event.target.value;
  }

  goToBookingList() {
    this.router.navigate(['/booking-list']);
  }

  getPrice() {
    this.bookingService
      .getPrice(this.booking.timeSlot, this.booking.seats)
      .subscribe((data) => {
        if(data.code=="VALERRCOD"){
          this.toastrService.error(data.message);
          this.goToBookingList
        }else{
        this.booking.price = data.details[0].price;
        }
      }
    );
  }

  removeValidationPrice() {
    this.validPrice = '';
  }
  removeValidationBookingDate() {
    this.validBookingDate = '';
  }
  removeValidationMovieName() {
    this.validMovieName = '';
  }
  removeValidationEmail() {
    this.validEmail = '';
  }
  removeValidationPhnNo() {
    this.validPhnNo = '';
  }
  removeValidationAge() {
    this.validAge = '';
  }
  removeValidationCustomerName() {
    this.validCustomerName = '';
  }
  removeValidationBookingId() {
    this.validBookingId = '';
  }
  removeValidationSeats() {
    this.validSeats = '';
  }
}
