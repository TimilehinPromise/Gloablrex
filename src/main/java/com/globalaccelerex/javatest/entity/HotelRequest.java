package com.globalaccelerex.javatest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class HotelRequest {

    private String hotel_name;

    private short ratings;

    private int totalsinglerooms;

    private int totaldoublerooms;

    private int bookedsinglerooms;

    private int bookeddoublerooms;

    private RoomType room_type;



}
