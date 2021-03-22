package com.fehead.roomBooking.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
房间状态类
id 状态对应时间 开始时间 结束时间 状态
 */
public class RoomStatus {
    private  Integer id;
    private Integer roomId;
    private Timestamp startStamp;
    private Timestamp endStamp;
    private Integer status;
}

