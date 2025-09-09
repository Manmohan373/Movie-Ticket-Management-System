import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ToastrService } from 'ngx-toastr';
import { TicketBooking } from 'src/app/models/ticket-booking';
import { TicketBookingService } from 'src/app/services/ticket-booking.service';
import 'datatables.net';
import * as $ from 'jquery';

@Component({
  selector: 'app-booking-list',
  templateUrl: './booking-list.component.html',
  styleUrls: ['./booking-list.component.css'],
})
export class BookingListComponent implements OnInit, OnDestroy {
  searchStatus: String;
  searchId: String;
  searchName: String;
  searchTimeSlot: String;
  searchPhnNo: String;
  endDate: any;
  startDate: any;
  validToken: any;
  selectedItem: TicketBooking;

  constructor(
    private toastrService: ToastrService,
    private http: HttpClient,
    private router: Router,
    private bookingService: TicketBookingService,
    private jwtservice: JwtHelperService
  ) {}
  ngOnInit(): void {
    localStorage.setItem('datasaved', 'no');
    this.initializeDataTable();
    this.validToken = setInterval(() => {
      if (
        localStorage.getItem('access_token') != null &&
        this.jwtservice.isTokenExpired(localStorage.getItem('access_token'))
      ) {
        this.router.navigateByUrl('login-page');
      }
    }, 500);
  }
  ngOnDestroy(): void {
    clearInterval(this.validToken);
  }

  initializeDataTable(): void {
    const table = $('#myTable').DataTable({
      serverSide: true,
      processing: false,
      searching: false,
      ordering: false,
      lengthMenu: [7, 10, 15, 20],
      ajax: (data: any, callback: any) => {
        this.http
          .get('http://localhost:9437/TicketBooking/searchBooking', {
            params: {
              iDisplayStart: (data.start / data.length).toString(),
              iDisplayLength: data.length.toString(),
              // searchParam: '',
              searchParam: JSON.stringify({
                bookingId: this.searchId,
                customerName: this.searchName,
                phnNo: this.searchPhnNo,
                timeSlot: this.searchTimeSlot,
                startDate: this.startDate,
                endDate: this.endDate,
                status: this.searchStatus,
              }),
            },
          })
          .subscribe((response: any) => {
            callback({
              draw: data.draw,
              recordsTotal: response.details[0].iTotalRecords,
              recordsFiltered: response.details[0].iTotalDisplayRecords,
              data: response.details[0].aaData,
            });
          });
      },
      columns: [
        { data: 'bookingId' },
        { data: 'customerName' },
        { data: 'phnNo' },
        { data: 'age' },
        { data: 'email' },
        { data: 'seats' },
        { data: 'movieName' },
        { data: 'timeSlot' },
        { data: 'bookingDate' },
        { data: 'price' },
        {
          data: 'status',
          render: function (data) {
            if (data == 'booked') {
              return '<span style="color:aquamarine;font-weight :500;">booked</span>';
            } else if (data == 'processing') {
              return '<span style="color:yellow;font-weight :500;">processing</span>';
            } else if (data == 'canceled') {
              return '<span style="color:red;font-weight :500;">canceled</span>';
            } else {
              return '<span style="color:grey;font-weight :500;">DELETED</span>';
            }
          },
        },
      ],
      rowCallback: (row: Node, data: any) => {
        $(row)
          .off('click')
          .on('click', () => {
            if ($(row).hasClass('selected')) {
              $('#myTable tr.selected').removeClass('selected');
            } else {
              $('#myTable tr.selected').removeClass('selected');
              $(row).addClass('selected');
            }
            this.onRowSelect(data);
          });
      },
    });
  }

  onRowSelect(booking: TicketBooking) {
    if (this.selectedItem === booking) {
      this.selectedItem = null;
    } else {
      this.selectedItem = booking;
    }
  }

  getSearchData(event: { 
    bookingId: String;
    customerName: String;
    phnNo: String;
    timeSlot: String;
    startDate: String;
    endDate: String;
    status: String;
  }) {
    this.searchId = event.bookingId;
    this.searchName = event.customerName;
    this.searchTimeSlot = event.timeSlot;
    this.searchPhnNo = event.phnNo;
    this.startDate = event.startDate;
    this.endDate = event.endDate;
    this.searchStatus = event.status;
    $('#myTable').DataTable().draw();
  }

  updateBooking() {

    if (this.selectedItem === undefined) {
      this.toastrService.info('you must select a row first...');
      return;
    } else {
      localStorage.setItem('datasaved', 'yes');
      this.router.navigate([
        'update-booking',
        this.selectedItem.bookingId,
        this.selectedItem.customerName,
        this.selectedItem.phnNo
      ]);
    }  }
  verify() {
    if (this.selectedItem === undefined) {
      this.toastrService.info('you must select a row first...');
      return;
    } else {
      localStorage.setItem('datasaved', 'yes');
      this.router.navigate([
        'verify-booking',
        this.selectedItem.bookingId,
        this.selectedItem.customerName,
        this.selectedItem.phnNo
      ]);
    }  }

  deleteBooking() {
    if (this.selectedItem === undefined) {
      this.toastrService.info('you must select a row first...');
      return;
    }
    this.bookingService
      .deleteBooking(
        this.selectedItem.bookingId,
        this.selectedItem.customerName,
        this.selectedItem.phnNo
      )
      .subscribe(
        (data) => {
         if(data.code == "DELETED"){
          this.toastrService.error(data.message);
         }else{
          this.toastrService.error(data.details[0].deletedStatus);
          $('#myTable').DataTable().draw();
         }

        },
        (error) => console.log(error)
      );
  }
}
