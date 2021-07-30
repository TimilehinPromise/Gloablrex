package com.globalaccelerex.javatest.service;

import com.globalaccelerex.javatest.Exception.ResourceNotFoundException;
import com.globalaccelerex.javatest.Repository.HotelRepository;
import com.globalaccelerex.javatest.Repository.ReservationRepository;
import com.globalaccelerex.javatest.entity.HotelRequest;
import com.globalaccelerex.javatest.entity.Hotels;
import com.globalaccelerex.javatest.entity.RoomType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    @Autowired
    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;


    public List<Hotels> getAllhotels (){
        List<Hotels> hotels = new ArrayList<>();
        hotelRepository.findAll().forEach(hotels::add);
        return hotels;
    }

    public Hotels getHotelById (Long hotel_id){

        Hotels hotels =this.hotelRepository.findById(hotel_id).get();

        if(hotels == null){
            throw new ResourceNotFoundException("Couldn't find Hotel with id::"+ hotel_id);
        }
        return hotelRepository.findById(hotel_id).get();

    }

    public Hotels saveHotel( HotelRequest request){
        Hotels oldetails = new Hotels();

        oldetails.setHotelName(request.getHotel_name());
        oldetails.setTotalSingleRooms(request.getTotalsinglerooms());
        oldetails.setTotalDoubleRooms(request.getTotaldoublerooms());
        oldetails.setAllocatedDoubleRooms(request.getBookeddoublerooms());
        oldetails.setAllocatedSingleRooms(request.getBookedsinglerooms());
        oldetails.setRatings(request.getRatings());
        oldetails.setRoomType(request.getRoom_type());
        int availabledoublerooms =  request.getTotaldoublerooms() - request.getBookeddoublerooms();
        int availablesinglerooms = request.getTotalsinglerooms() - request.getBookedsinglerooms();
        oldetails.setAvailableDoubleRooms(availabledoublerooms);
        oldetails.setAvailableSingleRooms((availablesinglerooms));


        oldetails.setTotalRooms(request.getTotalsinglerooms() + request.getTotaldoublerooms());
        hotelRepository.save(oldetails);


        return hotelRepository.save(oldetails);
    }

    public void updateHotel (Long hotel_id, HotelRequest request){

        Hotels oldetails= this.hotelRepository.findById(hotel_id).get();
        if(oldetails ==null){
            throw new ResourceNotFoundException("No such id as:"+ hotel_id);
        }
        oldetails.setHotelName(request.getHotel_name());
//        oldetails.setAllocated_rooms(hotels.getAllocated_rooms());
        oldetails.setTotalSingleRooms(request.getTotalsinglerooms());
        oldetails.setTotalDoubleRooms(request.getBookeddoublerooms());
        oldetails.setAllocatedDoubleRooms(request.getBookeddoublerooms());
        oldetails.setAllocatedSingleRooms(request.getBookedsinglerooms());
        oldetails.setRatings(request.getRatings());
       int availabledoublerooms =  request.getTotaldoublerooms() - request.getBookeddoublerooms();
       int availablesinglerooms = request.getTotalsinglerooms() -request.getBookedsinglerooms();
       oldetails.setAvailableDoubleRooms(availabledoublerooms);
       oldetails.setAllocatedSingleRooms(availablesinglerooms);

        oldetails.setTotalRooms(request.getTotalsinglerooms() + request.getTotaldoublerooms());
        hotelRepository.save(oldetails);
    }

    public void deleteHotel (Long hotelId){
        Hotels oldetails = this.hotelRepository.findById(hotelId).get();
        if(oldetails ==null){
            throw new ResourceNotFoundException("No such id as:"+ hotelId);
        }
        hotelRepository.deleteById(hotelId);


    }
    public List<Object[]> getHotelsbyRoomsandRatings(RoomType roomType , short ratings){
        List<Object[]> hotelsList = hotelRepository.findAllByRoomTypeAndRatings(roomType,ratings);
       return hotelsList;
    }

        public String getSummaryOfRooms (Long hotelId)
        {
          Hotels hotels =  hotelRepository.findHotelsByHotelId(hotelId);
        if (hotels.getRoomType().equals(RoomType.SINGLE)){
          return (hotels.getHotelName()+" hotel has  " +hotels.getTotalRooms()+" single rooms, " +hotels.getAllocatedSingleRooms()+
                  " allocated rooms, "+hotels.getAvailableSingleRooms()+ " available rooms");
        }
        else if (hotels.getRoomType().equals(RoomType.DOUBLE)){
        return (hotels.getHotelName()+ " hotel has " +hotels.getTotalRooms()+" double rooms, " + hotels.getAllocatedDoubleRooms()+
                " allocated rooms, " +hotels.getAvailableDoubleRooms()+ " available rooms");
        }
        else if (hotels.getRoomType().equals(RoomType.SINGLE_AND_DOUBLE)){
            return (hotels.getHotelName()+" hotel has "+hotels.getTotalSingleRooms()+" single " +hotels.getTotalDoubleRooms()+ "and double rooms, " +hotels.getAllocatedDoubleRooms()+
                    " allocated double rooms, "+hotels.getAvailableDoubleRooms()+ " available double rooms "+hotels.getAllocatedSingleRooms()+
                    " allocated single rooms, "+hotels.getAvailableSingleRooms()+ " available single rooms");
        }
        else
            return null;
}}


