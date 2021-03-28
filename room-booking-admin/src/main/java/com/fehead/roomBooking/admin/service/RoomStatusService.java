package com.fehead.roomBooking.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fehead.roomBooking.common.entity.Application;
import com.fehead.roomBooking.common.entity.RoomStatus;
import com.fehead.roomBooking.admin.mapper.RoomStatusMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
public class RoomStatusService {
    private RoomStatusMapper roomStatusMapper;

    public RoomStatusService(RoomStatusMapper roomStatusMapper) {
        this.roomStatusMapper = roomStatusMapper;
    }

    //添加房间状态
    public Boolean addRoomStatus(RoomStatus roomStatus){
        this.isParamEnough(roomStatus);
        int insert = roomStatusMapper.insert(roomStatus);
        if (insert!=0){
            log.info("申请增加成功");
            return true;
        }else {
            return false;
        }
    }
    //删除房间状态
    public  Boolean deleteRoomStatusById(int id){
        int delete = roomStatusMapper.deleteById(id);
        if (delete!=0){
            log.info("申请删除成功");
            return true;
        }else {
            return false;
        }
    }
    //返回所有房间状态
    public List<RoomStatus> getAllRoomStatus(){
        return roomStatusMapper.selectList(null);
    }
    //返回指定时间后的房间状态
    public List<RoomStatus> getRoomStatusByTime(Timestamp timestamp){
        QueryWrapper<RoomStatus> queryWrapper=new QueryWrapper<>();
        queryWrapper.ge("start_stamp",timestamp);
        return roomStatusMapper.selectList(queryWrapper);
    }
    //返回指定房间id的房间状态 分页
    public List<RoomStatus> getRoomStatusById(Integer id,Integer pageNum) {
        QueryWrapper<RoomStatus> queryWrapper = new QueryWrapper<>();
        Page<RoomStatus> roomStatusPage=new Page<>(pageNum,5);
        queryWrapper.ge("room_id", id);
         roomStatusMapper.selectPage(roomStatusPage,queryWrapper);
         return roomStatusPage.getRecords();
    }
    //修改指定房间id,指定状态id的房间状态
    public Boolean modifyRoomStatusById(RoomStatus roomStatus) {
        this.isParamEnough(roomStatus);
        int update = roomStatusMapper.updateById(roomStatus);
        if (update!=0){
            log.info("房间状态修改成功");
            return true;
        }else {
            log.warn("房间状态修改失败"+roomStatus);
            return false;
        }
    }
    //检查参数是否足够
    public Boolean isParamEnough(RoomStatus roomStatus){
        if (roomStatus.getRoomId()==null||

        roomStatus.getStartStamp()==null||
        roomStatus.getEndStamp()==null||
        roomStatus.getStatus()==null){
            throw new RuntimeException("数据缺失");
        }else
            return true;
    }
    //检查房间状态是否冲突
    public Boolean isRepeat( RoomStatus roomStatus){
        //获取房间状态 进行比较
        QueryWrapper<RoomStatus> queryWrapper=new QueryWrapper<>();
        //ge 大于等于 le小于等于
        queryWrapper.ge("start_stamp",roomStatus.getStartStamp()).or()
                .le("end_stamp",roomStatus.getEndStamp());
        List<RoomStatus> roomStatuses = roomStatusMapper.selectList(queryWrapper);
        //房间不可用
        queryWrapper.clear();
        queryWrapper.eq("status",1);

        if (roomStatuses.size()!=0){
            throw new RuntimeException("时间冲突");
        }
        return false;
    }
    }
