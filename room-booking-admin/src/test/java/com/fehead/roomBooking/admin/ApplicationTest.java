package com.fehead.roomBooking.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.roomBooking.common.entity.Application;
import com.fehead.roomBooking.common.entity.RoomStatus;
import com.fehead.roomBooking.admin.mapper.RoomStatusMapper;
import com.fehead.roomBooking.admin.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class ApplicationTest {
    @Autowired
    ApplicationService applicationService;
@Autowired
    RoomStatusMapper roomStatusMapper;
    @Test
    void add(){
        Date date=new Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        Application application=new Application(1,1,timestamp);
      //  applicationService.addApplication(application,);
        List<Application> allApplication = applicationService.getAllApplication(1);
        allApplication.forEach(System.out::println);
    }
    @Test
    void ifDouble(){
        Date date=new Date();
        Timestamp timestamp=new Timestamp(1616675382);
        QueryWrapper<RoomStatus> queryWrapper=new QueryWrapper<>();
        //ge 大于 lt小于
        queryWrapper.ge("start_stamp",timestamp);
        List<RoomStatus> roomStatuses = roomStatusMapper.selectList(queryWrapper);
        if (roomStatuses.size()==0){
            System.out.println("kong");
        }
        roomStatuses.forEach(System.out::println);
    }
}