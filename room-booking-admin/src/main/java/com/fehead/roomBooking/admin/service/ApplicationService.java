package com.fehead.roomBooking.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fehead.roomBooking.admin.mapper.ApplicationMapper;
import com.fehead.roomBooking.admin.mapper.RoomMapper;
import com.fehead.roomBooking.admin.mapper.RoomStatusMapper;
import com.fehead.roomBooking.common.entity.Application;
import com.fehead.roomBooking.common.entity.ApplicationReturnType;
import com.fehead.roomBooking.common.entity.RoomStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class ApplicationService {
    private ApplicationMapper applicationMapper;
    private RoomStatusMapper roomStatusMapper;
    private RoomMapper roomMapper;

    public ApplicationService(ApplicationMapper applicationMapper, RoomStatusMapper roomStatusMapper, RoomMapper roomMapper) {
        this.applicationMapper = applicationMapper;
        this.roomStatusMapper = roomStatusMapper;
        this.roomMapper = roomMapper;
    }

    /**
    增加application 同时添加对应房间状态  检查是否与其他申请的时间重合
     */
    public Boolean addApplication(Application application){
        Date date=new Date();
        if (application.getStartStamp()<=date.getTime()){
            throw new RuntimeException("不能提交过去的时间");
        }
        application.setStatus(0);
        application.setApplicationStamp(date.getTime());
        if (!(this.isDuplicate(application))) {
            this.roomStatus(application, 0);
            int insert = applicationMapper.insert(application);
            if (insert != 0) {
                log.info("申请增加成功");
                return true;
            } else {
                log.warn("申请插入失败");
               throw new  RuntimeException("申请插入失败");
            }
        }else {
            log.warn("申请的时间重复");
            throw new RuntimeException("申请的时间重复");
        }
    }
    //返回所有application的list  分页
    public ApplicationReturnType getAllApplication(int pageNum){
        //当前页 每页大小
        Page<Application> applicationPage=new Page<>(pageNum,5);
        QueryWrapper<Application> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("status","application_stamp");
        applicationMapper.selectPage(applicationPage,queryWrapper);
        List<Application> applications = applicationPage.getRecords();
        long time = new Date().getTime();
        applications.forEach(application -> {
            application.setRoom(roomMapper.selectById(application.getRoomId()));
            //返回前检查申请是否失效
            if(application.getEndStamp()<=time){
                //失效则更改状态 3
                application.setStatus(3);
                applicationMapper.updateById(application);
            }

        });

        //一共多少条 多少页
        ApplicationReturnType returnType = new ApplicationReturnType();
        returnType.setData(applications);
        returnType.setTotal(applicationPage.getTotal());
        returnType.setPageTotal(applicationPage.getPages());
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("status",0);
        returnType.setUnhandled(applicationMapper.selectList(wrapper).size());
        returnType.setStatus("success");
        return returnType;
    }
    //按照id返回
    public Application getApplicationById(int id){
        Application application =applicationMapper.selectById(id);
        application.setRoom(roomMapper.selectById(application.getRoomId()));
        long time = new Date().getTime();
        //返回前检查申请是否失效
        if(application.getEndStamp()<=time){
            //失效则更改状态 3
            application.setStatus(3);
            applicationMapper.updateById(application);
        }
        return application;
    }
    //按照条件返回信息相同的application的list
    public List<Application> getApplicationByMap(Map<String,String> map){
        QueryWrapper<Application> queryWrapper = new QueryWrapper<>();
        map.forEach(queryWrapper::like);
        List<Application> applications = applicationMapper.selectList(queryWrapper);
        long time = new Date().getTime();
        applications.forEach(application -> {
            application.setRoom(roomMapper.selectById(application.getRoomId()));
            //返回前检查申请是否失效
            if(application.getEndStamp()<=time){
                //失效则更改状态 3
                application.setStatus(3);
                applicationMapper.updateById(application);
            }
        });
        return applications;
    }
    //删除id对应的申请和房间状态
    public Boolean deleteById( Integer id){
        Application application = applicationMapper.selectById(id);
        Integer roomStatusId = application.getRoomStatusId();
        int deleteRoomStatus = roomStatusMapper.deleteById(roomStatusId);
        int deleteApplication = applicationMapper.deleteById(id);
        if (deleteApplication!=0&&deleteRoomStatus!=0){
            log.info("管理员删除了请求,id信息为"+id);
            return true;
        }else {
            return false;
        }
    }
    /**
     * id 修改application 可能需要修改对应的房间状态
     * @param id
     * @param application
     * @return
     */
    public Boolean modifyApplication(Integer id, Application application) {
        application.setId(id);
        //新增和修改时间时检查时间重复
        Application oldApplication=applicationMapper.selectById(application.getId());
        application.setRoomStatusId(oldApplication.getRoomStatusId());
        //修改对应的房间状态
        this.roomStatus(application,1);
        int update = applicationMapper.updateById(application);
        if (update!=0){
            log.info("管理员修改了请求,id为"+id);
            return true;
        }else {
            return false;
        }

    }

    //检查新申请的时间是否重复
    public Boolean isDuplicate(Application application){
        AtomicBoolean b = new AtomicBoolean(false);
        //获取房间状态 进行比较
        QueryWrapper<RoomStatus> queryWrapper=new QueryWrapper<>();
        //ge 大于等于 le小于等于 相同数据可能显示不重复
        queryWrapper.ge("start_stamp",application.getStartStamp());
             queryWrapper.le("end_stamp",application.getEndStamp());
        List<RoomStatus> roomStatuses = roomStatusMapper.selectList(queryWrapper);
        if (roomStatuses.size()==0){
            log.info("申请未重复");
          return false;
        }else {
            //逻辑问题
            //如果只有相同id的一条数据 则返回false
            for (RoomStatus roomStatus : roomStatuses){
                if ( roomStatus.getStatus()==0){
                     log.info("房间已经使用");
                    return true;
            }
            }
        }
        return false;

    }
    //添加或修改对应房间状态 0 1
    public Boolean roomStatus(Application application, int i){
        RoomStatus roomStatus=new RoomStatus();
        roomStatus.setId(application.getRoomStatusId());
        roomStatus.setRoomId(application.getRoomId());
        roomStatus.setStartStamp(application.getStartStamp());
        roomStatus.setEndStamp(application.getEndStamp());
        //通过则房间不可用
        //房间 0 不可用 1 可用
        if (application.getStatus()>=1){
            log.info("修改房间为不可用");
            roomStatus.setStatus(0);
        }else {
            roomStatus.setStatus(1);
        }
        System.out.println(application);
        System.out.println(roomStatus);
        int insert=0;
        switch (i){
            case 0:
                 insert = roomStatusMapper.insert(roomStatus);
                 application.setRoomStatusId(roomStatus.getId());
                if (insert!=0){
                    log.info("添加房间状态成功");
                }else {
                    throw new  RuntimeException("房间状态插入时出现问题");
                }
                break;
            case 1:
                insert = roomStatusMapper.updateById(roomStatus);
//                if (insert!=0){
//                    log.info("修改房间状态成功");
//                }else {
//                    throw new  RuntimeException("房间状态修改时出现问题");
//                }
                log.info("修改房间状态成功");
                break;
        }

        return  true;
    }
    //序列化为正常的long

}
