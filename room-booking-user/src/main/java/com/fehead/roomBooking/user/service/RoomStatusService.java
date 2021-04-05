package com.fehead.roomBooking.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.roomBooking.common.entity.RoomStatus;
import com.fehead.roomBooking.user.mapper.AvailableDayMapper;
import com.fehead.roomBooking.user.mapper.RoomStatusMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.swing.BakedArrayList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class RoomStatusService {
    private RoomStatusMapper roomStatusMapper;
    @Autowired
    private AvailableDayMapper availableDayMapper;

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

    /**
     * 查看每月的情况
     * @param dateStr
     * @param roomId
     * @return
     * @throws ParseException
     */
    public Map<String,List<String>> getRoomStatusMonthly(String dateStr,int roomId) throws ParseException {
        final int SECOND_OF_DAY = 86400000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = format.parse(dateStr);
        long timeStamp = date.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        QueryWrapper<RoomStatus> wrapper = new QueryWrapper<>();
        wrapper.eq("room_id",roomId)
                .between("start_stamp",timeStamp,timeStamp+ (long) SECOND_OF_DAY *dayOfMonth);
        List<String> full = new ArrayList<>(dayOfMonth);
        List<String> notFull = new ArrayList<>(dayOfMonth);
        List<RoomStatus> list = roomStatusMapper.selectList(wrapper);

        //whirabbit
        Map<Integer,List> map=new HashMap<>();
        list.forEach(roomStatus -> {
            Long startTime = roomStatus.getStartStamp();
            Long endTime= roomStatus.getEndStamp();
            calendar.setTime(new Date(roomStatus.getStartStamp()));
            //具体某天
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            //这一天开始的时间戳
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            long todayTime=0;
            try {
                 todayTime= format1.parse(dateStr+"-"+day).getTime();
            } catch (ParseException e) {
                log.warn("时间转换出错");
            }

            if (startTime >= todayTime+(9*3600000)&&endTime<=todayTime+(11*3600000)) {
                if( map.containsKey(day)){
                    map.get(day).add(1);
                }else {
                    List<Integer> list1=new ArrayList<>();
                    list1.add(1);
                    map.put(day,list1);
                }
            }

            else if (startTime >= todayTime+(14*3600000)&&endTime <= todayTime+(16*3600000) ) {
               if( map.containsKey(day)){
                   map.get(day).add(2);
               }else {
                   List<Integer> list1=new ArrayList<>();
                   list1.add(2);
                   map.put(day,list1);
               }
            }
            else if (startTime >= todayTime+(16*3600000)&&endTime <= todayTime+(18*3600000) ) {
                if( map.containsKey(day)){
                    map.get(day).add(3);
                }else {
                    List<Integer> list1=new ArrayList<>();
                    list1.add(3);
                    map.put(day,list1);
                }
            }

            else if (startTime >= todayTime+(18*3600000)&&endTime <= todayTime+(20*3600000) ){
                if( map.containsKey(day)){
                    map.get(day).add(4);
                }else {
                    List<Integer> list1=new ArrayList<>();
                    list1.add(4);
                    map.put(day,list1);
                }
            }
        });

        map.forEach((integer, list1) -> {
            if (list1.size()>=4){
                full.add(dateStr+"-"+integer);
            }else if(list1.size()>0) {
                notFull.add(dateStr+"-"+integer);
            }
        });

        Map<String,List<String>> res = new HashMap<>();
        res.put("full",full);
        res.put("notFull",notFull);

        return res;

    }
    /**
     *
     * @param dateStr
     * @param roomId
     * @return  返回被占用数 和占用的具体时间 []
     * @throws ParseException
     */
    public Map<String,Object> getRoomStatusByDate(String dateStr,Integer roomId) throws ParseException {
        final long SECOND_OF_DAY = 86400000;
        Map<String,Object> map=new HashMap<>();
        map.put("roomId",roomId);
        map.put("dateStr",dateStr);
        //查询某天的状态
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateStr);
        //有问题
        long todayTime=date.getTime();
        QueryWrapper<RoomStatus> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("room_id",roomId).between("start_stamp",todayTime,todayTime+SECOND_OF_DAY );
        List<RoomStatus> roomStatuses = roomStatusMapper.selectList(queryWrapper);
      //  int [] dayStatus = new int[4];
        List<Integer>  dayStatus=new ArrayList<>();
        roomStatuses.forEach(roomStatus -> {
            Long startTime = roomStatus.getStartStamp();
            Long endTime= roomStatus.getEndStamp();
//
            if (startTime >= todayTime+(9*3600000)&&endTime<=todayTime+(11*3600000)){
               if ( !dayStatus.contains(1)) {
                    dayStatus.add(1);
                }
            }
            else if (startTime >= todayTime+(14*3600000)&&endTime <= todayTime+(16*3600000) ){
                if ( !dayStatus.contains(2)) {
                    dayStatus.add(2);
                }
            }
            else if (startTime >= todayTime+(16*3600000)&&endTime <= todayTime+(18*3600000) ) {
                if ( !dayStatus.contains(3)) {
                    dayStatus.add(3);
                }
            }
            else if (startTime >= todayTime+(18*3600000)&&endTime <= todayTime+(20*3600000) ){
                if ( !dayStatus.contains(4)) {
                    dayStatus.add(4);
                }
            }
        });

        map.put("occupyNum",dayStatus.size());
        map.put("dayStatus",dayStatus);
        return map;
    }
    //返回每周可用时间
    public List  availableDay(){
       return availableDayMapper.selectList(null);
    }

}
