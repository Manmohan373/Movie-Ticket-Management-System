package com.example.demo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor@NoArgsConstructor@ToString
@Getter@Builder
@Table(name = "time_slot")
public class TimeSlot {

    @Id
    private String timeSlots;
}
