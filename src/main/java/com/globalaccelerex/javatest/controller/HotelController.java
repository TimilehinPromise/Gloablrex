package com.globalaccelerex.javatest.controller;

import com.globalaccelerex.javatest.entity.HotelRequest;
import com.globalaccelerex.javatest.entity.Hotels;
import com.globalaccelerex.javatest.entity.RoomType;
import com.globalaccelerex.javatest.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("api/v1/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<Hotels>> getAllHotels(){

        List<Hotels> hotels =hotelService.getAllhotels();
        return new ResponseEntity<>(hotels, HttpStatus.FOUND);
    }

    @GetMapping({"/{hotel_id}"})
    public ResponseEntity <Hotels> getHotels(@PathVariable ("hotel_id")Long hotel_id){
        return new ResponseEntity<>(hotelService.getHotelById(hotel_id),HttpStatus.FOUND);
    }

    @GetMapping("summary/{hotelId}")

    public ResponseEntity <Hotels> getHotelSummary(@PathVariable ("hotelId")Long hotelId){
        try {
            return new ResponseEntity(hotelService.getSummaryOfRooms(hotelId),HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping({"/{roomType}/{ratings}"})
    private ResponseEntity<Hotels> getHotelsByRoomsandRatings(@PathVariable ("roomType")RoomType roomType,@PathVariable("ratings") short ratings){
        try {
            return new ResponseEntity(hotelService.getHotelsbyRoomsandRatings(roomType,ratings),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            }
    @PostMapping
    public ResponseEntity saveHotel(@Valid @RequestBody HotelRequest request){
        try {
            Hotels hotels1 = hotelService.saveHotel(request);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("hotel","/api/v1/hotels"+hotels1.getHotelId().toString());
            return new ResponseEntity<>(hotels1, httpHeaders, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{hotel_id}")
    public ResponseEntity updateHotel (@Valid @RequestBody HotelRequest request, @PathVariable("hotel_id") Long hotel_id){
       try{ hotelService.updateHotel(hotel_id,request);
        return new ResponseEntity<>("Succesfully updated ",HttpStatus.OK);
    }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @DeleteMapping("/{hotel_id}")
    public ResponseEntity deleteHotel (@PathVariable("hotel_id") Long hotel_id){
        try {
           hotelService.deleteHotel(hotel_id);
            return new ResponseEntity("Succesfully deleted with all reservations ",HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
