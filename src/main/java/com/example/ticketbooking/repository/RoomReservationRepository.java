package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

    @Query("select rr from RoomReservation rr where rr.childrenAmount <= :childrenAmount")
    List<RoomReservation> findByChildrenAmount(Long childrenAmount);

    @Query("select rr from RoomReservation rr where rr.adultAmount <= :adultAmount")
    List<RoomReservation> findByAdultAmount(Long adultAmount);
}
