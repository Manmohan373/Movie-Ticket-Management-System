package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookingDto;
import com.example.demo.dto.ServiceResponse;
import com.example.demo.exception.IdAlreadyExistsException;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.exception.StatusDeletedException;
import com.example.demo.exception.invalidParameterException;
import com.example.demo.service.TicketBookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/TicketBooking")
@CrossOrigin(origins = "http://localhost:4200/")
@RequiredArgsConstructor
public class TicketBookingController {
    private final TicketBookingService bookingService;

    @PostMapping("/bookTicket")
    public ResponseEntity<ServiceResponse> addEmployee(@Valid @RequestBody BookingDto bookingDto)
	    throws IdAlreadyExistsException {
	ServiceResponse result = bookingService.bookTicket(bookingDto);
	return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/searchBooking")
    public ResponseEntity<ServiceResponse> searchBooking(@RequestParam String searchParam,
	    @RequestParam String iDisplayStart, @RequestParam String iDisplayLength) {
	ServiceResponse list = bookingService.searchBooking(searchParam, iDisplayStart, iDisplayLength);
	return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/getById/{bookingId}/{customerName}/{phnNo}")
    public ResponseEntity<ServiceResponse> getMethodName(@PathVariable String bookingId,
	    @PathVariable String customerName, @PathVariable String phnNo)
	    throws StatusDeletedException, RecordNotFoundException {
	return new ResponseEntity<>(bookingService.getById(bookingId, customerName, phnNo), HttpStatus.OK);
    }

    @PutMapping("/updateBooking/{bookingId}/{customerName}/{phnNo}")
    public ResponseEntity<ServiceResponse> updateBooking(@PathVariable String bookingId,
	    @PathVariable String customerName, @PathVariable String phnNo, @Valid @RequestBody BookingDto bookingDto)
	    throws RecordNotFoundException {
	ServiceResponse result = bookingService.updateBooking(bookingId, customerName, phnNo, bookingDto);
	return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getTimeSlot")
    public ResponseEntity<ServiceResponse> getTimeSlot() {
	ServiceResponse result = bookingService.getTimeSlot();
	return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/cancelBooking/{bookingId}/{customerName}/{phnNo}")
    public ResponseEntity<ServiceResponse> cancelBooking(@PathVariable String bookingId,
	    @PathVariable String customerName, @PathVariable String phnNo)
	    throws StatusDeletedException, RecordNotFoundException {
	ServiceResponse result = bookingService.cancelBooking(bookingId, customerName, phnNo);
	return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("verify/{bookingId}/{customerName}/{phnNo}")
    public ResponseEntity<ServiceResponse> verify(@PathVariable String bookingId, @PathVariable String customerName,
	    @PathVariable String phnNo) {
	ServiceResponse result = bookingService.verify(bookingId, customerName, phnNo);
	return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getPrice/{timeSlot}/{seats}")
    public ResponseEntity<ServiceResponse> getPrice(@PathVariable String timeSlot, @PathVariable int seats)
	    throws invalidParameterException {
	ServiceResponse result = bookingService.getPrice(timeSlot, seats);
	return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
