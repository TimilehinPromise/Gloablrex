package com.globalaccelerex.javatest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ReservationRequest {


    @Column(nullable = false,unique = true)
    private String customer_name;
    private Long hotelId;
    private RoomType room_type;
    private int doublerooms;
    private int singlerooms;
}
