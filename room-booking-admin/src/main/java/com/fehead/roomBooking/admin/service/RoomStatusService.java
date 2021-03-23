package com.fehead.roomBooking.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.roomBooking.admin.entity.RoomStatus;
import com.fehead.roomBooking.admin.mapper.RoomStatusMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
            log.info("申请增加成功");
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
    public List<RoomStatus> getRoomStatusByTime(){
        QueryWrapper<RoomStatus> queryWrapper=new QueryWrapper<>();
        queryWrapper.ge("","");
        return roomStatusMapper.selectList(queryWrapper);
    }
    //返回指定房间id的房间状态
    public List<RoomStatus> getRoomStatusById(Integer id) {
        QueryWrapper<RoomStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("room_id", id);
        return roomStatusMapper.selectList(queryWrapper);
    }
    //修改指定房间id,指定状态id的房间状态
    public Boolean changeRoomStatusById(Integer roomId,RoomStatus roomStatus) {
        int update = roomStatusMapper.updateById(roomStatus);
        if (update!=0){
            log.info("房间状态添加成功");
            return true;
        }else {
            log.warn("房间状态添加失败"+roomStatus);
            return false;
        }
    }
    }
