package com.fehead.roomBooking.admin.service;

import com.fehead.roomBooking.admin.entity.Room;
import com.fehead.roomBooking.admin.mapper.RoomMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoomService {
    private RoomMapper roomMapper;
    @Autowired
    public RoomService(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    //更改房间描述
    public Boolean changeRoom(Room room){

        int update = roomMapper.update(room, null);
        if (update!=0){
            log.info("更改房间信息成功"+room);
            return true;
        }else{
            return false;
        }
    }

}
