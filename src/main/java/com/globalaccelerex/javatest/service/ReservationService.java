package com.globalaccelerex.javatest.service;

import com.globalaccelerex.javatest.Exception.ResourceNotFoundException;
import com.globalaccelerex.javatest.Repository.HotelRepository;
import com.globalaccelerex.javatest.Repository.ReservationRepository;
import com.globalaccelerex.javatest.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class ReservationService {

    int defaultno =0;
    private final ReservationRepository reservationRepository;

    private final HotelRepository hotelRepository;

public List<Hotel_Reservation> GetAllReservations(){
    List<Hotel_Reservation> reservations = new ArrayList<>();
    reservationRepository.findAll().forEach(reservations::add);
    return reservations;

}

public Hotel_Reservation getReservationsById (Long reservation_id){

    Hotel_Reservation reservation =this.reservationRepository.findById(reservation_id).get();

    if(reservation==null){
       throw new ResourceNotFoundException(("Couldn't find reservation with id::"+reservation_id));
    }
return reservationRepository.findById(reservation_id).get();
}


public Hotel_Reservation saveReservation ( ReservationRequest request){

    Hotels hotel = hotelRepository.findById(request.getHotelId()).get();
    if (hotel.getRoomType().equals(RoomType.DOUBLE)){
        bookDoubleRooms(request);}
    else if (hotel.getRoomType().equals(RoomType.SINGLE)){
        bookSinglerooms(request);
    }
   else if (hotel.getRoomType().equals(RoomType.SINGLE_AND_DOUBLE)){
        bookSingleandDoubleRooms(request);
    }
   else
    {throw new IllegalArgumentException("Room Type Not valid");}

    Hotel_Reservation reservation =    new Hotel_Reservation();
  reservation.setHotel(hotel);
  reservation.setCustomerName(request.getCustomer_name());
  reservation.setReservationId(0L);
  reservation.setRoomType(request.getRoom_type());
        hotelRepository.save(hotel);
        reservationRepository.save(reservation);

    return null;
}



    public boolean isSingleRoomAvailable(ReservationRequest request){
    Hotels hotel = hotelRepository.findById(request.getHotelId()).get();

    boolean available = ( hotel.getAvailableSingleRooms()>= request.getSinglerooms());

        return available;
}

public boolean isDoubleRoomAvailable(ReservationRequest request){
    Hotels hotel = hotelRepository.findById(request.getHotelId()).get();
    boolean available = ( hotel.getAvailableDoubleRooms()>= request.getDoublerooms());

        return available;
}

public void updateReservation (Long reservation_id, Hotel_Reservation reservation){
    Hotel_Reservation oldetails = this.reservationRepository.findById(reservation_id).get();
    if (oldetails == null){
        throw new ResourceNotFoundException("No such id as:"+ reservation_id);
    }

    oldetails.setCustomerName(reservation.getCustomerName());
    oldetails.setHotel(reservation.getHotel());
    oldetails.setDoubleRooms(reservation.getDoubleRooms());
    oldetails.setSingleRooms(reservation.getSingleRooms());
    reservationRepository.save(reservation);
}

public void deleteReservation (Long reservation_id){
    reservationRepository.deleteById(reservation_id);
}

public void bookDoubleRooms(ReservationRequest request){
    Hotels hotel = hotelRepository.findById(request.getHotelId()).get();
    if (!Objects.isNull(hotel)){

    Hotel_Reservation reservation =    new Hotel_Reservation();
    if(request.getRoom_type().equals(RoomType.DOUBLE) && isDoubleRoomAvailable(request)){
        hotel.setAllocatedDoubleRooms(hotel.getAllocatedDoubleRooms() + request.getDoublerooms());
        hotel.setAvailableDoubleRooms(hotel.getAvailableDoubleRooms()- request.getDoublerooms());
        hotel.setTotalSingleRooms(defaultno);
        hotel.setAvailableSingleRooms(defaultno);
        hotel.setAllocatedSingleRooms(defaultno);
        reservation.setSingleRooms(defaultno);
        reservation.setDoubleRooms(request.getDoublerooms());

    }
    else throw new IllegalArgumentException("No enough double rooms for request");

}}

    public void bookSinglerooms (ReservationRequest request){
        Hotels hotel = hotelRepository.findById(request.getHotelId()).get();
        if (!Objects.isNull(hotel)){

            Hotel_Reservation reservation =    new Hotel_Reservation();
            if(request.getRoom_type().equals(RoomType.SINGLE) && (isSingleRoomAvailable(request))){

                hotel.setAllocatedSingleRooms(hotel.getAllocatedSingleRooms() + request.getSinglerooms());
                hotel.setAvailableSingleRooms(hotel.getAvailableSingleRooms() - request.getSinglerooms());
                hotel.setTotalDoubleRooms(defaultno);
                hotel.setAvailableDoubleRooms(defaultno);
                hotel.setAllocatedDoubleRooms(defaultno);
                reservation.setDoubleRooms(defaultno);
                reservation.setSingleRooms(request.getSinglerooms());
            }else throw new IllegalArgumentException("Unavailable Single rooms");
    }
}
    private void bookSingleandDoubleRooms(ReservationRequest request) {
        Hotels hotel = hotelRepository.findById(request.getHotelId()).get();
        if (!Objects.isNull(hotel)){

            Hotel_Reservation reservation =    new Hotel_Reservation();
            if (request.getRoom_type().equals(RoomType.SINGLE_AND_DOUBLE)){
                if (isSingleRoomAvailable(request) && isDoubleRoomAvailable(request)){

                    hotel.setAllocatedSingleRooms(hotel.getAllocatedSingleRooms() + request.getSinglerooms());
                    hotel.setAvailableSingleRooms(hotel.getAvailableSingleRooms() - request.getSinglerooms());
                    hotel.setAllocatedDoubleRooms(hotel.getAllocatedDoubleRooms() + request.getDoublerooms());
                    hotel.setAvailableDoubleRooms(hotel.getAvailableDoubleRooms() - request.getDoublerooms());
                    reservation.setSingleRooms(request.getSinglerooms());
                    reservation.setDoubleRooms(request.getDoublerooms());
                }else throw new IllegalArgumentException("Unavailable Single or Double rooms");
            }
    }
}}
