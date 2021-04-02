package com.fehead.roomBooking.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.roomBooking.common.entity.RoomStatus;
import com.fehead.roomBooking.user.mapper.RoomStatusMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public Map<String,List<String>> getRoomStatusMonthly(String dateStr,int roomId) throws ParseException {
        final int SECOND_OF_DAY = 86400;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = format.parse(dateStr);
        long timeStamp = date.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        QueryWrapper<RoomStatus> wrapper = new QueryWrapper<>();
        wrapper.eq("id",roomId)
                .between("start_stamp",timeStamp,timeStamp+ (long) SECOND_OF_DAY *dayOfMonth);

        List<String> full = new ArrayList<>(dayOfMonth);
        List<String> notFull = new ArrayList<>(dayOfMonth);

        List<RoomStatus> list = roomStatusMapper.selectList(wrapper);
        boolean[] dayStatus = new boolean[4];
        for (int i = 0; i < dayOfMonth; i++) {
            Arrays.fill(dayStatus,false);
            int finalI = i;
            list.stream()
                    .filter(s -> s.getStartStamp() >= timeStamp+((long) SECOND_OF_DAY *(finalI - 1)) && s.getEndStamp() <= timeStamp+((long) finalI *SECOND_OF_DAY))
                    .forEach(r ->{
                        long startTime = r.getStartStamp();
                        long endTime = r.getEndStamp();
                        long todayTime = timeStamp+((long) SECOND_OF_DAY *(finalI - 1));

                        if (startTime >= todayTime+(9*3600)) dayStatus[0] = true;
                        else if (startTime >= todayTime+(14*3600)) dayStatus[1] = true;
                        else if (startTime >= todayTime+(16*3600)) dayStatus[2] = true;
                        else if (startTime >= todayTime+(18*3600)) dayStatus[3] = true;

                        if (endTime <= todayTime+(11*3600)) dayStatus[0] = true;
                        else if (endTime <= todayTime+(16*3600)) dayStatus[1] = true;
                        else if (endTime <= todayTime+(18*3600)) dayStatus[2] = true;
                        else if (endTime <= todayTime+(20*3600)) dayStatus[3] = true;

                        boolean isFull = true;
                        for (int j = 0; j < 4; j++) {
                            if (!dayStatus[j]) {
                                isFull = false;
                                break;
                            }
                        }
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                        if (isFull) full.add(format1.format(timeStamp+((long) finalI *SECOND_OF_DAY)));
                        else notFull.add(format1.format(timeStamp+((long) finalI *SECOND_OF_DAY)));
                    });
        }

        Map<String,List<String>> res = new HashMap<>();
        res.put("full",full);
        res.put("notFull",notFull);

        return res;

    }
    //日期 id
    //返回被占用数 和具体时间 []
    //基本信息 天 房间

    /**
     *
     * @param dateStr
     * @param roomId
     * @return  返回被占用数 和具体时间 []
     * @throws ParseException
     */
    public Map<String,Object> getRoomStatusByDate(String dateStr,Integer roomId) throws ParseException {
        final long SECOND_OF_DAY = 86400;
        Map<String,Object> map=new HashMap<>();
        map.put("roomId",roomId);
        map.put("dateStr",dateStr);
        //查询某天的状态
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateStr);
        long todayTime= date.getTime();
        QueryWrapper<RoomStatus> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("room_id",roomId).between("start_stamp",todayTime,todayTime+SECOND_OF_DAY );
        List<RoomStatus> roomStatuses = roomStatusMapper.selectList(queryWrapper);
        boolean[] dayStatus = new boolean[4];
        roomStatuses.forEach(roomStatus -> {
            Long startTime = roomStatus.getStartStamp();
            Long endTime= roomStatus.getEndStamp();
//
            if (startTime >= todayTime+(9*3600)&&endTime<=todayTime+(11*3600)) dayStatus[0] = true;
            else if (startTime >= todayTime+(14*3600)&&endTime <= todayTime+(16*3600) ) dayStatus[1] = true;
            else if (startTime >= todayTime+(16*3600)&&endTime <= todayTime+(18*3600) ) dayStatus[2] = true;
            else if (startTime >= todayTime+(18*3600)&&endTime <= todayTime+(20*3600) ) dayStatus[3] = true;
        });
        int occupyNum =0;
        for (boolean b: dayStatus){
             if (b) occupyNum++;
        }
        map.put("occupyNum",occupyNum);
        map.put("dayStatus",dayStatus);
        return map;
    }
}
