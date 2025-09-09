package com.example.demo.service;

import com.example.demo.dto.BookingDto;
import com.example.demo.dto.ServiceResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.IdAlreadyExistsException;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.exception.StatusDeletedException;
import com.example.demo.exception.UsernameNotFoundException;
import com.example.demo.exception.invalidParameterException;

import jakarta.validation.Valid;

public interface TicketBookingService {


    ServiceResponse bookTicket(@Valid BookingDto bookingDto) throws IdAlreadyExistsException;

    ServiceResponse searchBooking(String searchParam, String iDisplayStart, String iDisplayLength);

    ServiceResponse getById(String bookingId, String customerName, String phnNo) throws StatusDeletedException, RecordNotFoundException;

    ServiceResponse updateBooking(String bookingId, String customerName, String phnNo, @Valid BookingDto bookingDto) throws RecordNotFoundException;

    ServiceResponse getTimeSlot();

    ServiceResponse cancelBooking(String bookingId, String customerName, String phnNo) throws StatusDeletedException, RecordNotFoundException;

    ServiceResponse verify(String bookingId, String customerName, String phnNo);

    ServiceResponse getPrice(String timeSlots, int seats) throws invalidParameterException;

}
