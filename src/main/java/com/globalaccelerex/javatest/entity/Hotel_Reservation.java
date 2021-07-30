package com.globalaccelerex.javatest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableGenerator(name="tab", initialValue=100000, allocationSize=50)

public class Hotel_Reservation {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="tab")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private Long reservationId;

    private String customerName;

    @Column
    @NotNull
    private RoomType roomType;

    @Column
    private int doubleRooms;

    @Column
    private int singleRooms;

    @ManyToOne
    @JoinColumn (name = "hotel_id", nullable = false)
    private Hotels hotel;







}
