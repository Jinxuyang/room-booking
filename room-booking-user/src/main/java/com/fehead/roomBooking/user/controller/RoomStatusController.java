package com.fehead.roomBooking.user.controller;

import com.fehead.roomBooking.common.controller.BaseController;
import com.fehead.roomBooking.common.entity.RoomStatus;
import com.fehead.roomBooking.common.response.CommonReturnType;
import com.fehead.roomBooking.user.service.RoomStatusService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomStatusController extends BaseController {
    private RoomStatusService roomStatusService;

    public RoomStatusController(RoomStatusService roomStatusService) {
        this.roomStatusService = roomStatusService;
    }

    /**
     * 获取指定id教室的所有状态信息
     */
    @GetMapping("/{roomId}/status")
    public CommonReturnType getAllRoomStatus(@PathVariable("roomId") Integer roomId,
                                             @RequestParam(name = "date",required = false) String date) throws ParseException {
        if (date == null) return CommonReturnType.create(roomStatusService.getRoomStatusByRoomId(roomId));
        else return CommonReturnType.create(roomStatusService.getRoomStatusMonthly(date,roomId));
    }

    @GetMapping("/{roomId}/status/{StatusId}")
    public CommonReturnType getARoomStatusById(@PathVariable("roomId") Integer roomId,
                                               @PathVariable("StatusId")Integer StatusId ){
        RoomStatus roomStatusById = roomStatusService.getRoomStatusById(roomId,StatusId);
        return  CommonReturnType.create(roomStatusById);
    }
    /**
     * 获取指定id教室的某天的可用状态
     */
    @GetMapping("/{roomId}")
    public CommonReturnType getRoomStatusByDate(@PathVariable("roomId") Integer roomId,
                                                String dateStr) throws ParseException {
        return CommonReturnType.create(roomStatusService.getRoomStatusByDate(dateStr,roomId));
    }

    /**
     * 获取可申请时间
     * @return
     */
    @GetMapping
    public CommonReturnType getAvailableDay(){
        return CommonReturnType.create(roomStatusService.availableDay());
    }
}
