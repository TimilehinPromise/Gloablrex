package com.globalaccelerex.javatest.Repository;

import com.globalaccelerex.javatest.entity.Hotels;
import com.globalaccelerex.javatest.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface HotelRepository extends JpaRepository<Hotels,Long> {

    List<Object[]> findAllByRoomTypeAndRatings(RoomType room_type, short ratings);

    Hotels findHotelsByHotelId(Long hotelId);


//Hotels getHotelsByHotel_id( Long id);


}
