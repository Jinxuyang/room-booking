package com.fehead.roomBooking.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
申请类
id 申请人id 对应的房间状态id 申请时间
 */
public class Application {
   @TableId(type= IdType.AUTO)
   private   Integer id;
   private  Integer userId;
   private  Integer roomStatusId;
   private Timestamp applicationStamp;

   public Application(Integer userId, Integer roomStatusId, Timestamp applicationStamp) {
      this.userId = userId;
      this.roomStatusId = roomStatusId;
      this.applicationStamp = applicationStamp;
   }
}
