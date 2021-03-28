package com.fehead.roomBooking.user.controller;

import com.fehead.roomBooking.common.controller.BaseController;
import com.fehead.roomBooking.common.entity.RoomStatus;
import com.fehead.roomBooking.common.response.CommonReturnType;
import com.fehead.roomBooking.user.service.RoomStatusService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomStatusController extends BaseController {
    private RoomStatusService roomStatusService;

    public RoomStatusController(RoomStatusService roomStatusService) {
        this.roomStatusService = roomStatusService;
    }

    /*
        获取指定id教室的所有状态信息
         */
    @GetMapping("/{roomId}/statuses")
    public CommonReturnType getAllRoomStatus(@PathVariable("roomId") Integer roomId){
        List<RoomStatus> roomStatusById = roomStatusService.getRoomStatusByRoomId(roomId);
        return  CommonReturnType.create(roomStatusById);
    }

    @GetMapping("/{roomId}/statuses/{StatusId}")
    public CommonReturnType getARoomStatusById(@PathVariable("roomId") Integer roomId,
                                               @PathVariable("StatusId")Integer StatusId ){
        RoomStatus roomStatusById = roomStatusService.getRoomStatusById(roomId,StatusId);
        return  CommonReturnType.create(roomStatusById);
    }
}
