package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.TimeSlot;
@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, String> {

}
