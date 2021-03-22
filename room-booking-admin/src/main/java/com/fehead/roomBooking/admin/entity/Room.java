package com.fehead.roomBooking.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
房间类 id 房间名称 房间描述
 */
public class Room {
    private Integer id;
    private String roomName;
    private  String describe;
}
