package com.globalaccelerex.javatest.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@TableGenerator(name="tab", initialValue=100000, allocationSize=50)
public class Hotels {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="tab")
    @Column(name = "hotel_id",length = 36, updatable = false, nullable = false)
    private Long hotelId;


    @Column(unique = true)
    private String hotelName;

    @Max(5)
    @Min(0)
    @Column
    @NotNull
    private short ratings;

    @Column
    @NotNull

    private int totalSingleRooms;

    @Column
    @NotNull

    private int totalDoubleRooms;

    @Column

    private int allocatedSingleRooms;

    @Column

    private int availableSingleRooms;

    @Column
    private RoomType roomType;

    @Column

    private int allocatedDoubleRooms;

    @Column

    private int availableDoubleRooms;

    @Column

    private int totalRooms;

    @JsonIgnore
    @OneToMany(mappedBy = "hotel",cascade = CascadeType.REMOVE)
    private Set<Hotel_Reservation> hotel_reservation;


}
