package com.globalaccelerex.javatest.controller;

import com.globalaccelerex.javatest.entity.Hotel_Reservation;
import com.globalaccelerex.javatest.entity.ReservationRequest;
import com.globalaccelerex.javatest.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Hotel_Reservation>> getAllReservations () {
        try {
            List<Hotel_Reservation> reservations = reservationService.GetAllReservations();
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{reservation_id}")
    public ResponseEntity<Hotel_Reservation> getReservations (@PathVariable("reservation_id")Long reservation_id){
       try {
           return new ResponseEntity<>(reservationService.getReservationsById(reservation_id),HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }}

    @PostMapping
    public ResponseEntity saveReservation(@Valid @RequestBody ReservationRequest requestBody){
        try{
        Hotel_Reservation reservation= reservationService.saveReservation(requestBody);
       return new ResponseEntity<>(reservation,HttpStatus.CREATED);}
        catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{reservation_id}")
    public ResponseEntity updateReservation (@Valid @RequestBody Hotel_Reservation reservation,@PathVariable("reservation_id")Long reservation_id)
    {   try {
        {
            reservationService.updateReservation(reservation_id,reservation);
            return new ResponseEntity<>("Succesfully updated",HttpStatus.OK);
        }
    }catch (Exception e){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @DeleteMapping("/{reservation_id}")
    public ResponseEntity deleteReservation (@PathVariable("reservation_id")Long reservation_id){
        try {
            reservationService.deleteReservation(reservation_id);
            return new ResponseEntity("Succesfuly deleted", HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
