package com.fehead.roomBooking.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.roomBooking.common.entity.Application;
import com.fehead.roomBooking.common.entity.RoomStatus;
import com.fehead.roomBooking.user.mapper.ApplicationMapper;
import com.fehead.roomBooking.user.mapper.RoomStatusMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ApplicationService {
    private ApplicationMapper applicationMapper;
    private RoomStatusMapper roomStatusMapper;
    @Autowired
    public ApplicationService(ApplicationMapper applicationMapper, RoomStatusMapper roomStatusMapper) {
        this.applicationMapper = applicationMapper;
        this.roomStatusMapper = roomStatusMapper;
    }

    /*
    增加application 检查是否与其他申请的时间重合
     */
    public Boolean addApplication(Application application){
        if (!this.isRepeat(application)) {
            int insert = applicationMapper.insert(application);
            if (insert != 0) {
                log.info("申请增加成功");
                return true;
            } else {
                log.warn("申请插入失败");
                return false;
            }
        }else {
            log.warn("申请的时间重复");
            return false;
        }
    }
    //id 修改application 可能需要修改对应的房间状态
    public Boolean modifyApplication(Integer id, Application application) {
        application.setId(id);
        int update = applicationMapper.updateById(application);
        if (update!=0){
            log.info("管理员修改了请求,id为"+id);
            return true;
        }else {
            return false;
        }
    }
    //检查新申请的时间是否重复
    public Boolean isRepeat(Application application){
        //获取房间状态 进行比较
        QueryWrapper<RoomStatus> queryWrapper=new QueryWrapper<>();
        //ge 大于等于 le小于等于
        queryWrapper.ge("start_stamp",application.getStartStamp())
                .le("end_stamp",application.getEndStamp());
        List<RoomStatus> roomStatuses = roomStatusMapper.selectList(queryWrapper);
        if (roomStatuses.size()==0){
            log.info("申请未重复");
            return  false;
        }
        return true;
    }
}
