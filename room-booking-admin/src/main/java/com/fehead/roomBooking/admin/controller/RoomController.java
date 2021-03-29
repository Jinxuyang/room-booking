package com.fehead.roomBooking.admin.controller;

import com.fehead.roomBooking.common.entity.RoomStatus;
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
    public CommonReturnType getStatus(@PathVariable("roomId") Integer id,
                                      Integer pageNum ) {
        if (pageNum==null){
            throw new RuntimeException("pageNum参数缺失");
        }

        List<RoomStatus> roomStatusById = roomStatusService.getRoomStatusById(id,pageNum);

        return   CommonReturnType.create(roomStatusById);

    }
    /*
    为指定id教室新增状态
     */
    @PostMapping("/{roomId}/status")
    public CommonReturnType addStatus(@PathVariable("roomId") String roomId,
                                      @RequestBody RoomStatus roomStatus)
    {

        roomStatusService.addRoomStatus(roomStatus);
        return CommonReturnType.create("添加房间状态成功");
    }
    /*
    修改指定id状态
     */
    @PutMapping("/{roomId}/status/{statusId}")
    public CommonReturnType modifyStatus(@PathVariable("roomId") Integer roomId,
                                         @PathVariable("statusId") Integer statusId,
                                         @RequestBody RoomStatus roomStatus)
    {
        roomStatus.setRoomId(roomId);
        roomStatus.setId(statusId);
        Boolean changeRoomStatusById = roomStatusService.modifyRoomStatusById(roomStatus);
        if (changeRoomStatusById){

            log.info("管理员修改了房间状态 id="+statusId);
            return CommonReturnType.create("修改成功");
        }else {
            log.info("房间状态修改失败");
          return  CommonReturnType.create("修改失败","fail");
        }
    }
    /*
    删除指定id状态
     */
    @DeleteMapping("/{roomId}/status/{statusId}")
    public CommonReturnType deleteStatus(@PathVariable("roomId") Integer roomId,
                                         @PathVariable("statusId") Integer statusId)
    {
        Boolean deleteRoomStatusById = roomStatusService.deleteRoomStatusById(statusId);
        if (deleteRoomStatusById){
            log.info("管理员删除了房间状态 id="+statusId);
         return    CommonReturnType.create("删除成功");
        }
        return  CommonReturnType.create("删除失败");
    }
}
