package com.fehead.roomBooking.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
房间状态类
id 状态对应时间 开始时间 结束时间 状态
 */
@ToString
public class RoomStatus {
    @TableId(type= IdType.AUTO)
    private  Integer id;
    private Integer roomId;
    //是否需要userid 不需要
   // private Integer userId;
    @NotNull(message = "开始时间不能为空")
    private Long startStamp;
    @NotNull(message = "结束时间不能为空")
    private Long endStamp;
    //0 不可用 1 可用
    @NotNull(message = "状态不能为空")
    private Integer status;
}

