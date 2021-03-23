package com.fehead.roomBooking.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fehead.roomBooking.admin.entity.RoomStatus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoomStatusMapper extends BaseMapper<RoomStatus> {
}
