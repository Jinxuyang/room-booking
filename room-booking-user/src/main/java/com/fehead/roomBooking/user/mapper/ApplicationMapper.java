package com.fehead.roomBooking.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fehead.roomBooking.common.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ApplicationMapper extends BaseMapper<Application> {
}
