package com.fehead.roomBooking.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fehead.roomBooking.common.entity.AvailableDay;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author whirabbit
 * @Date 2021/4/2 19:12
 * @Version 1.0
 */
@Mapper
@Repository
public interface AvailableDayMapper extends BaseMapper<AvailableDay> {
}
