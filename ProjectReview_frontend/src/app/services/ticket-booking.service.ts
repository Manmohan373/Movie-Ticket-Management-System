import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { TicketBooking } from '../models/ticket-booking';

@Injectable({
  providedIn: 'root',
})
export class TicketBookingService {

  private getAccessTokenURL = 'http://localhost:9437/TicketBooking/getAccessToken';
  private getTimeSlotURL = 'http://localhost:9437/TicketBooking/getTimeSlot';
  private getByIdURL = 'http://localhost:9437/TicketBooking/getById';
  private bookTicketURL = 'http://localhost:9437/TicketBooking/bookTicket';
  private updateBookingURL = 'http://localhost:9437/TicketBooking/updateBooking';
  private verifyBookingURL = 'http://localhost:9437/TicketBooking/verify';
  private deleteBookingURL = 'http://localhost:9437/TicketBooking/cancelBooking';
  private getPriceURL = "http://localhost:9437/TicketBooking/getPrice"

  constructor(private httpclient: HttpClient, private router: Router) {}

  bookTicket(booking: TicketBooking) {
    return this.httpclient.post<any>(`${this.bookTicketURL}`, booking);
  }

  getAccessToken(user: User) {
    return this.httpclient.post<any>(`${this.getAccessTokenURL}`, user);
  }
  getTimeSlot() {
    return this.httpclient.get<any>(`${this.getTimeSlotURL}`);
  }
  getById(bookingId: any, customerName: any, phnNo: any) {
    return this.httpclient.get<any>(
      `${this.getByIdURL}/${bookingId}/${customerName}/${phnNo}`
    );
  }
  verifyBooking(bookingId: String, customerName: String, phnNo: String) {
    return this.httpclient.put<any>(`${this.verifyBookingURL}/${bookingId}/${customerName}/${phnNo}`,null);
  }
  updateTicket(
    bookingId: any,
    customerName: any,
    phnNo: any,
    booking: TicketBooking
  ) {
    return this.httpclient.put<any>(`${this.updateBookingURL}/${bookingId}/${customerName}/${phnNo}`,booking);
  }
  deleteBooking(bookingId: String, customerName: String, phnNo: String) {
    return this.httpclient.delete<any>(
      `${this.deleteBookingURL}/${bookingId}/${customerName}/${phnNo}`
    );
  }

  getPrice(timeSlot: String, seats: number) {
    return this.httpclient.get<any>(`${this.getPriceURL}/${timeSlot}/${seats}`);
  }
}
