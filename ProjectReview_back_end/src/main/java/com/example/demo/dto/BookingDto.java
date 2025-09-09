package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class BookingDto {
    @validId
    private String bookingId;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]{2,30}$", message = "name shouldn't contain digits")
    private String customerName;
    @NotBlank
    @Pattern(regexp = "^[789]\\d{9}$", message = "phone no shouldn contain 10digits")
    private String phnNo;
    @NotNull
    @Min(18)
    private int age;
    @Email
    @NotBlank
    private String email;
    @NotNull
    @Min(value = 1,message = "minimum value should be 1")
    private int seats;
    @NotBlank
    private String movieName;
    @NotBlank
    private String timeSlot;
    @NotNull
    @FutureOrPresent
    private LocalDate bookingDate;
    private Double price;
    private LocalDateTime createdTime;
    private String status;
    private String createdUser;

}
