package com.globalaccelerex.javatest.Repository;

import com.globalaccelerex.javatest.entity.Hotel_Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ReservationRepository extends JpaRepository<Hotel_Reservation, Long> {


}
