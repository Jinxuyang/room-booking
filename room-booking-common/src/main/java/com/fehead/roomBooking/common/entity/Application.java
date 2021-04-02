package com.fehead.roomBooking.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fehead.roomBooking.common.validation.Create;
import com.fehead.roomBooking.common.validation.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
申请类
id 申请人id 对应的房间状态id 申请时间 房间id
 */

public class Application implements Serializable {
   @TableId(type= IdType.AUTO)
   @NotNull(groups = {Update.class},message = "更新时id不能为空")
   private   Integer id;
   private  Integer userId;//
   private  Integer roomStatusId;
   @TableField(fill = FieldFill.INSERT)
   private Long applicationStamp;
   @NotNull(message = "申请房间不能为空")
   private Integer roomId;//
   @TableField(exist = false)
   private Room room;
   //申请审批状态 0 1 2 未审核 通过 已使用
   @TableField(fill = FieldFill.INSERT)
   private Integer status;
   //表单数据
   @NotNull(message = "申请人不能为空",groups = {Create.class})
   private String applicant;
   @NotNull(message = "学院不能为空",groups = {Create.class})
   private String  college;
   @NotNull(message = "班级不能为空",groups = {Create.class})
   private String classes;
   //身份
   @NotNull(message = "身份不能为空",groups = {Create.class})
   private String identity;
   @Email(message = "邮箱格式不正确",groups = {Create.class})
   @NotNull(message = "邮箱不能为空",groups = {Create.class})
   private String  email;
   @NotNull(message = "电话不能为空",groups = {Create.class})
   @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误",groups = {Create.class})
   private String phoneNumber;
   //学号 工号
   @NotNull(message = "学号/工号不能为空",groups = {Create.class})
   private String  jobNumber;
   @NotNull(message = "开始时间不能为空",groups = {Create.class})
   private Long startStamp;
   @NotNull(message = "结束时间不能为空",groups = {Create.class})
   private Long endStamp;
   @NotNull(message = "申请原因不能为空",groups = {Create.class})
   private String  reasonsForApplication;
   private String remarks;
}
