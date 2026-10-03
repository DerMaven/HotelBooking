package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select t from Transaction t join Booking b on t.booking.id = b.id join Guest g on b.guest.id = g.id where g.firstName = :firstName and g.lastName = :lastName order by t.processedAt desc")
    List<Transaction> findByFullName(String firstName, String lastName);
}
