package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.BookingDto;
import com.example.demo.dto.ServiceResponse;
import com.example.demo.entity.BookingPk;
import com.example.demo.entity.TicketBooking;
import com.example.demo.entity.TimeSlot;
import com.example.demo.entity.User;
import com.example.demo.exception.IdAlreadyExistsException;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.exception.StatusDeletedException;
import com.example.demo.exception.UsernameNotFoundException;
import com.example.demo.exception.invalidParameterException;
import com.example.demo.repository.TicketBookingRepository;
import com.example.demo.repository.TimeSlotRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.specification.TicketBookingSpecification;
import com.example.demo.utils.Constants;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class TicketBookingServiceImplementation implements TicketBookingService {


    private final TicketBookingRepository bookingRepo;
    private final TimeSlotRepository timeSlotRepo;
    private final MessageSource messageSource;


    @Override
    public ServiceResponse bookTicket(@Valid BookingDto bookingDto) throws IdAlreadyExistsException {
	BookingPk id = setPrimarykeys(bookingDto.getBookingId(), bookingDto.getCustomerName(), bookingDto.getPhnNo());
	Optional<TicketBooking> optional = bookingRepo.findById(id);
	if (optional.isPresent()) {
	    throw new IdAlreadyExistsException();
	}
	try {
	    double price = getTotalPrice(bookingDto.getTimeSlot(), bookingDto.getSeats());
	    TicketBooking booking = convertDtotoEntity(bookingDto, id, price, Constants.PROCESSING,
		    LocalDateTime.now());
	    JSONArray array = new JSONArray();
	    array.add(bookingRepo.save(booking));
	    return new ServiceResponse(Constants.SUCCESS,
		    messageSource.getMessage("messages.val001", null, LocaleContextHolder.getLocale()), array);
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
	return new ServiceResponse(Constants.FAILED,
		messageSource.getMessage("messages.val002", null, LocaleContextHolder.getLocale()),
		Collections.emptyList());
    }

    private TicketBooking convertDtotoEntity(@Valid BookingDto bookingDto, BookingPk id, double price, String status,
	    LocalDateTime createdTime) {
 	return TicketBooking.builder().id(id).age(bookingDto.getAge()).email(bookingDto.getEmail( ))
		.seats(bookingDto.getSeats()).movieName(bookingDto.getMovieName()).timeSlot(bookingDto.getTimeSlot())
		.bookingDate(bookingDto.getBookingDate()).price(price).createdTime(createdTime).status(status)
		.createdUser(null).build();
    }

    private BookingPk setPrimarykeys(String bookingId, String customerName, String phnNo) {
	return BookingPk.builder().bookingId(bookingId).customerName(customerName).phnNo(phnNo).build();
    }

    @Override
    public ServiceResponse searchBooking(String searchParam, String iDisplayStart, String iDisplayLength) {
	try {
	    int start = Integer.parseInt(iDisplayStart);
	    int pageSize = Integer.parseInt(iDisplayLength);
	    JSONObject result = new JSONObject();
	    getSpecificationData(start, pageSize, searchParam, result);
	    return new ServiceResponse(Constants.SUCCESS,
		    messageSource.getMessage("messages.val003", null, LocaleContextHolder.getLocale()),
		    List.of(result));
	} catch (Exception e) {
	    log.error("Error during search: " + e.getMessage());
	}
	return new ServiceResponse(Constants.FAILED,
		messageSource.getMessage("messages.val004", null, LocaleContextHolder.getLocale()),
		Collections.emptyList());
    }

    private void getSpecificationData(int start, int pageSize, String searchParam, JSONObject result) {
	try {
	    Pageable pageable = PageRequest.of(start, pageSize);
	    Specification<TicketBooking> specification = TicketBookingSpecification.getStreamBySearchSpec(searchParam);
	    Page<TicketBooking> streamList = bookingRepo.findAll(specification, pageable);
	    long count = bookingRepo.count(specification);
	    JSONArray array = new JSONArray();
	    for (TicketBooking booking : streamList) {
		array.add(convertEntityToDto(booking));
	    }
	    result.put(Constants.AA_DATA, array);
	    result.put(Constants.TOTAL_DISPLAY_RECORD, count);
	    result.put(Constants.TOTAL_RECORD, count);
	} catch (Exception e) {
	    log.error("Error during getSpecificationData: " + e.getMessage());
	}
    }

    private BookingDto convertEntityToDto(TicketBooking booking) {
	return BookingDto.builder().bookingId(booking.getId().getBookingId())
		.customerName(booking.getId().getCustomerName()).phnNo(booking.getId().getPhnNo()).age(booking.getAge())
		.email(booking.getEmail()).seats(booking.getSeats()).movieName(booking.getMovieName())
		.timeSlot(booking.getTimeSlot()).bookingDate(booking.getBookingDate()).price(booking.getPrice())
		.createdTime(booking.getCreatedTime()).status(booking.getStatus()).build();
    }

    @Override
    public ServiceResponse getById(String bookingId, String customerName, String phnNo)
	    throws StatusDeletedException, RecordNotFoundException {
	try {
	    Optional<TicketBooking> optionalBooking = bookingRepo
		    .findById(setPrimarykeys(bookingId, customerName, phnNo));
	    checkBooking(optionalBooking);
	    JSONArray array = new JSONArray();
	    array.add(convertEntityToDto(optionalBooking.get()));
	    return new ServiceResponse(Constants.SUCCESS,
		    messageSource.getMessage("messages.val003", null, LocaleContextHolder.getLocale()), array);
	} catch (StatusDeletedException | RecordNotFoundException e) {
	    log.error(e.getMessage());
	    throw e;
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
	return new ServiceResponse(Constants.FAILED,
		messageSource.getMessage("messages.val004", null, LocaleContextHolder.getLocale()),
		Collections.emptyList());
    }

    private void checkBooking(Optional<TicketBooking> optionalBooking)
	    throws RecordNotFoundException, StatusDeletedException {
	if (!optionalBooking.isPresent()) {
	    throw new RecordNotFoundException();
	}
	TicketBooking booking = optionalBooking.get();
	if (booking.getStatus().equals(Constants.DELETED) || booking.getStatus().equals(Constants.CANCELED)) {
	    throw new StatusDeletedException();
	}
    }

    @Override
    public ServiceResponse updateBooking(String bookingId, String customerName, String phnNo,
	    @Valid BookingDto bookingDto) throws RecordNotFoundException {

	if (!bookingDto.getBookingId().equals(bookingId) || !bookingDto.getCustomerName().equals(customerName)
		|| !bookingDto.getPhnNo().equals(phnNo)) {
	    throw new RecordNotFoundException("Id can't be updated");
	}
	try {
	    BookingPk id = setPrimarykeys(bookingDto.getBookingId(), bookingDto.getCustomerName(),
		    bookingDto.getPhnNo());
	    double price = getTotalPrice(bookingDto.getTimeSlot(), bookingDto.getSeats());
	    TicketBooking booking = convertDtotoEntity(bookingDto, id, price, bookingDto.getStatus(),
		    bookingDto.getCreatedTime());
	    JSONArray array = new JSONArray();
	    array.add(bookingRepo.save(booking));
	    return new ServiceResponse(Constants.SUCCESS,
		    messageSource.getMessage("messages.val005", null, LocaleContextHolder.getLocale()), array);
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
	return new ServiceResponse(Constants.FAILED,
		messageSource.getMessage("messages.val006", null, LocaleContextHolder.getLocale()),
		Collections.emptyList());
    }

    @Override
    public ServiceResponse getTimeSlot() {
	try {
	    List<TimeSlot> slots = timeSlotRepo.findAll();
	    JSONObject obj = new JSONObject();
	    obj.put("timeSlots", slots);
	    return new ServiceResponse(Constants.SUCCESS,
		    messageSource.getMessage("messages.val003", null, LocaleContextHolder.getLocale()), List.of(obj));
	} catch (Exception e) {
	    log.error(e.getMessage());
	    return new ServiceResponse(Constants.FAILED,
		    messageSource.getMessage("messages.val004", null, LocaleContextHolder.getLocale()),
		    Collections.emptyList());
	}
    }

    @Override
    public ServiceResponse cancelBooking(String bookingId, String customerName, String phnNo) throws StatusDeletedException, RecordNotFoundException {
	try {
	    Optional<TicketBooking> optionalBooking = bookingRepo
		    .findById(setPrimarykeys(bookingId, customerName, phnNo));
	    checkBooking(optionalBooking);
	    JSONObject obj = new JSONObject();
	    bookingRepo.save(setStatus(optionalBooking.get(), obj));
	    return new ServiceResponse(Constants.SUCCESS,
		    messageSource.getMessage("messages.val007", null, LocaleContextHolder.getLocale()), List.of(obj));
	} catch (StatusDeletedException | RecordNotFoundException e) {
	    log.error(e.getMessage());
	    throw e;
	}catch (Exception e) {
	    log.error(e.getMessage());
	}
	return new ServiceResponse(Constants.FAILED,
		messageSource.getMessage("messages.val008", null, LocaleContextHolder.getLocale()),
		Collections.emptyList());
    }

    private TicketBooking setStatus(TicketBooking existing, JSONObject obj) {
	String status = existing.getStatus();
	String deletedMessage = switch (status) {
	case Constants.PROCESSING, Constants.BOOKED -> "Booking deleted!!!";
	case Constants.DELETED -> "Already Deleted!!!";
	case Constants.CANCELED -> "Already Canceled!!!";
	default -> null;
	};
	if (deletedMessage != null) {
	    obj.put(Constants.DELETED_STATUS, deletedMessage);

	    if (status.equals(Constants.PROCESSING)) {
		return statusUpdate(existing, Constants.DELETED);
	    } else if (status.equals(Constants.BOOKED)) {
		return statusUpdate(existing, Constants.CANCELED);
	    }
	}
	return existing;

    }

    private TicketBooking statusUpdate(TicketBooking existing, String status) {
//	return TicketBooking.builder().id(existing.getId()).age(existing.getAge()).email(existing.getEmail())
//		.movieName(existing.getMovieName()).timeSlot(existing.getTimeSlot())
//		.bookingDate(existing.getBookingDate()).price(existing.getPrice())
//		.createdTime(existing.getCreatedTime()).status(status).build();
	return existing.toBuilder().status(status).build();
    }

    @Override
    public ServiceResponse verify(String bookingId, String customerName, String phnNo) {
	try {
	    Optional<TicketBooking> optionalBooking = bookingRepo
		    .findById(setPrimarykeys(bookingId, customerName, phnNo));
	    checkBooking(optionalBooking);
	    TicketBooking existing = optionalBooking.get();
	    JSONObject obj = new JSONObject();
	    verifyStatus(obj, existing);
	    return new ServiceResponse(Constants.SUCCESS,
		    messageSource.getMessage("messages.val009", null, LocaleContextHolder.getLocale()), List.of(obj));
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
	return new ServiceResponse(Constants.FAILED,
		messageSource.getMessage("messages.val010", null, LocaleContextHolder.getLocale()),
		Collections.emptyList());
    }

    private void verifyStatus(JSONObject obj, TicketBooking existing) {
	if (existing.getStatus().equals(Constants.BOOKED)) {
	    obj.put(Constants.BOOKING_STATUS, "already verified");
	} else if (existing.getStatus().equals(Constants.CANCELED) || existing.getStatus().equals(Constants.DELETED)) {
	    obj.put(Constants.BOOKING_STATUS, "canceled data can't be verified");
	} else {
	    bookingRepo.save(statusUpdate(existing, Constants.BOOKED));
	    obj.put(Constants.BOOKING_STATUS, "verified successfully");
	}
    }

    @Override
    public ServiceResponse getPrice(String timeSlots, int seats) throws invalidParameterException {
	if (seats <= 0) {
	    throw new invalidParameterException("No. of seats must be greaterthan Zero");
	}
	try {
	    int price = getTotalPrice(timeSlots, seats);
	    JSONObject object = new JSONObject();
	    object.put("price", price);
	    return new ServiceResponse(Constants.SUCCESS,
		    messageSource.getMessage("messages.val011", null, LocaleContextHolder.getLocale()),
		    List.of(object));
	} catch (NumberFormatException e) {
	    log.error(e.getMessage());
	}
	return new ServiceResponse(Constants.FAILED,
		messageSource.getMessage("messages.val011", null, LocaleContextHolder.getLocale()),
		Collections.emptyList());
    }

    private int getTotalPrice(String timeSlots, int seats) {
	try {
	    switch (timeSlots) {
	    case "08.30a.m-11.30a.m":
		return seats * 200;
	    case "12.30p.m-03.30p.m":
		return seats * 500;
	    case "04.30p.m-07.30p.m":
		return seats * 500;
	    case "08.30p.m-11.30p.m":
		return seats * 1000;
	    default:
		return 0;
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
	return 0;
    }
}