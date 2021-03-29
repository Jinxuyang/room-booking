package com.fehead.roomBooking.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
申请类
id 申请人id 对应的房间状态id 申请时间 房间id
 */

public class Application {
   @TableId(type= IdType.AUTO)
   private   Integer id;
   private  Integer userId;
   private  Integer roomStatusId;
   @NotNull(message = "申请时间不能为空")
   private Timestamp applicationStamp;
   @NotNull(message = "申请房间不能为空")
   private Integer roomId;
   //申请审批状态
   @NotNull(message = "状态不能为空")
   private Integer status;
   //表单数据
   @NotNull(message = "申请人不能为空")
   private String applicant;
   @NotNull(message = "学院不能为空")
   private String  college;
   @NotNull(message = "班级不能为空")
   private String classes;
   //身份
   @NotNull(message = "身份不能为空")
   private String identity;
   @Email(message = "邮箱格式不正确")
   @NotNull(message = "邮箱不能为空")
   private String  email;
   @NotNull(message = "电话不能为空")
   @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
   private String phone_number;
   //学号 工号
   @NotNull(message = "学号/工号不能为空")
   private String  job_number;
   @NotNull(message = "开始时间不能为空")
   private Timestamp startStamp;
   @NotNull(message = "结束时间不能为空")
   private Timestamp endStamp;
   @NotNull(message = "申请原因不能为空")
   private String  reasons_for_application;
   private String remarks;
   private String equipment;


   public Application(Integer userId, Integer roomStatusId, Timestamp applicationStamp) {
      this.userId = userId;
      this.roomStatusId = roomStatusId;
      this.applicationStamp = applicationStamp;
   }
}