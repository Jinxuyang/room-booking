package com.fehead.roomBooking.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.roomBooking.common.entity.RoomStatus;
import com.fehead.roomBooking.user.mapper.RoomStatusMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoomStatusService {
    private RoomStatusMapper roomStatusMapper;
    @Autowired
    public RoomStatusService(RoomStatusMapper roomStatusMapper) {
        this.roomStatusMapper = roomStatusMapper;
    }
    //返回指定房间id的房间状态
    public List<RoomStatus> getRoomStatusByRoomId(Integer roomId) {
        QueryWrapper<RoomStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("room_id", roomId);
        return roomStatusMapper.selectList(queryWrapper);
    }
    public RoomStatus getRoomStatusById(Integer roomId,Integer id) {
        QueryWrapper<RoomStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("id", id);
        queryWrapper.ge("room_id", roomId);
        return roomStatusMapper.selectOne(queryWrapper);
    }
}
