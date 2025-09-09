package com.example.demo.entity;

import java.io.Serializable;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Builder
@ToString
public class BookingPk implements Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    @Column(name = "booking_id")
    private String bookingId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "phn_no")
    private String phnNo;

}
