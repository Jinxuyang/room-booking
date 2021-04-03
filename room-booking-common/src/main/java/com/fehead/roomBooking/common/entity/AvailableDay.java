package com.fehead.roomBooking.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @Author whirabbit
 * @Date 2021/4/2 19:04
 * @Version 1.0
 */
@Data
@TableName("available_application_time")
public class AvailableDay {
    @JsonIgnore
    @TableId(type= IdType.AUTO)
    private int id;
    private  String name;
    private  int isAvailable;

}
