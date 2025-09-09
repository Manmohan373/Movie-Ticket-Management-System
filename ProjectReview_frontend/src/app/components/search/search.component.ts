import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { TicketBookingService } from 'src/app/services/ticket-booking.service';
import 'daterangepicker';
declare var $:any;
@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  @Output() sendSearchData = new EventEmitter<{
    bookingId: String;
    customerName: String;
    phnNo: String;
    timeSlot: String;
    startDate: String;
    endDate: String;
    status: String;
  }>();

  timeSlots: Array<any>;
  searchStatus: String = '';
  searchId: String;
  searchName: String;
  searchTimeSlot: String = '';
  searchPhnNo: String;
  endDate: any;
  startDate: any;

  constructor(private bookingService: TicketBookingService) {}

  ngOnInit(): void {
    this.bookingService.getTimeSlot().subscribe((data) => {
      this.timeSlots = data.details[0].timeSlots;
    });
    $('#dateRangePicker').daterangepicker(
      {
        opens: 'left',
        linkedCalendars: false,
      },
      (start: any, end: any, label: string) => {
        console.log(
          'Start Date:',
          start.format('YYYY-MM-DD'),
          'End Date:',
          end.format('YYYY-MM-DD')
        );
        this.startDate = start.format('YYYY-MM-DD');
        this.endDate = end.format('YYYY-MM-DD');
      }
    );
  }

  onCourseSelect(event: any) {
    this.searchTimeSlot = event.target.value;
  }
  clear() {
    this.searchId = '';
    this.searchTimeSlot = '';
    this.searchName='';
    this.searchPhnNo = '';
    this.searchStatus = '';
    this.startDate = '';
    this.endDate = '';
    this.onSubmit();
  }
  onSubmit() {
    const data = {
      bookingId: this.searchId,
      customerName: this.searchName,
      phnNo: this.searchPhnNo,
      timeSlot: this.searchTimeSlot,
      startDate: this.startDate,
      endDate: this.endDate,
      status: this.searchStatus,
    };
    this.sendSearchData.emit(data);
  }
}
