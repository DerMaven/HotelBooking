package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("select count(b.id) AS total_visits from hotels h left join bookings b on b.hotel_id = h.id where h.name = :name group by h.id, h.name")
    Long findVisitsByHotelName(String name);

    @Query("select h.* from hotels h join reviews r on r.hotel_id = h.id where city = :city group by h.id, h.name order by average_rating desc, total_reviews desc limit :limit")
    List<Hotel> findTopNBestHotelsInCity(String city, Integer limit);
}
