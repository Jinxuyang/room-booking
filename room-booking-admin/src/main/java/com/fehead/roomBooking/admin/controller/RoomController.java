package com.fehead.roomBooking.admin.controller;

import com.fehead.roomBooking.admin.entity.RoomStatus;
import com.fehead.roomBooking.admin.mapper.RoomStatusMapper;
import com.fehead.roomBooking.admin.service.RoomStatusService;
import com.fehead.roomBooking.common.controller.BaseController;
import com.fehead.roomBooking.common.response.CommonReturnType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/rooms")
public class RoomController extends BaseController {
    private RoomStatusService roomStatusService;

    @Autowired
    public RoomController(RoomStatusService roomStatusService ) {
        this.roomStatusService = roomStatusService ;
    }

    /*
        获取指定id教室的所有状态信息
         */
    @GetMapping("/{roomId}/status")
    public CommonReturnType getStatus(@PathVariable("roomId") Integer id)
    {
        CommonReturnType returnType=new CommonReturnType();
        List<RoomStatus> roomStatusById = roomStatusService.getRoomStatusById(id);
        returnType.setData(roomStatusById);
        returnType.setStatus("200");
        return returnType;
    }
    /*
    为指定id教室新增状态
     */
    @PostMapping("/{roomId}/status")
    public CommonReturnType addStatus(@PathVariable("roomId") String roomId,
                                      @RequestBody RoomStatus roomStatus)
    {
        CommonReturnType returnType=new CommonReturnType();
        roomStatusService.addRoomStatus(roomStatus);
        returnType.setStatus("200");
        returnType.setData("添加房间状态成功");
        return returnType;
    }
    /*
    修改指定id状态
     */
    @PutMapping("/{roomId}/status/{statusId}")
    public CommonReturnType changeStatus(@PathVariable("roomId") Integer roomId,
                                         @PathVariable("statusId") String statusId,
                                         @RequestBody RoomStatus roomStatus)
    {
        CommonReturnType returnType=new CommonReturnType();
        Boolean changeRoomStatusById = roomStatusService.changeRoomStatusById(roomId, roomStatus);
        if (changeRoomStatusById){
            returnType.setData("修改成功");
            returnType.setStatus("200");
        }
        return returnType;
    }
    /*
    删除指定id状态
     */
    @DeleteMapping("/{roomId}/status/{statusId}")
    public CommonReturnType deleteStatus(@PathVariable("roomId") Integer roomId,
                                         @PathVariable("statusId") Integer statusId)
    {
        CommonReturnType returnType=new CommonReturnType();
        Boolean deleteRoomStatusById = roomStatusService.deleteRoomStatusById(statusId);
        if (deleteRoomStatusById){
            returnType.setData("删除成功");
            returnType.setStatus("200");
        }
        return returnType;
    }
}
