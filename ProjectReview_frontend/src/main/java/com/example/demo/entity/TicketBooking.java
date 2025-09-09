package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "ticket_booking")
public class TicketBooking {
    @Id
    @EmbeddedId
    @Column(name = "id")
    private BookingPk id;  
    @Column(name = "age")
    private int age;
    @Column(name = "email")
    private String email;
    @Column(name = "seats")
    private int seats;
    @Column(name = "movie_name")
    private String movieName;
    @Column(name = "time_slot")
    private String timeSlot;
    @Column(name = "booking_date")
    private LocalDate bookingDate;
    @Column(name = "price")
    private Double price;
    @Column(name = "created_time")
    private LocalDateTime createdTime;
    @Column(name = "status")
    private String status;
    @Column(name = "created_user")
    private String createdUser;

}
