package com.fehead.roomBooking.user.controller;

import com.fehead.roomBooking.common.controller.BaseController;
import com.fehead.roomBooking.common.entity.Application;
import com.fehead.roomBooking.common.response.CommonReturnType;
import com.fehead.roomBooking.common.validation.Create;
import com.fehead.roomBooking.common.validation.Update;
import com.fehead.roomBooking.user.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController extends BaseController {
    @Autowired
    ApplicationService applicationService;

    @GetMapping
    public CommonReturnType getApplication( Integer userId, Integer pageNum ){
        if (userId==null){
           throw new RuntimeException("userId不能为空" );
        }else if (pageNum==null){
           throw new RuntimeException("pageNum不能为空");
        }else
            return CommonReturnType.create( applicationService.getApplication(userId,pageNum));
    }

    /**
     *     新增申请 时间重复问题`
     * @param application
     * @return
     */
    @PostMapping
    public CommonReturnType addApplication(@Validated(Create.class) @RequestBody Application application){

        Boolean addApplication = applicationService.addApplication(application);
        if (addApplication){
            return CommonReturnType.create("增加成功");
        }
            return CommonReturnType.create("增加失败");
    }
    /**
    修改申请modify
    */
    @PutMapping("/{applicationId}")
    public CommonReturnType modifyApplication(@Validated(Update.class) @RequestBody Application application,
                                              @PathVariable("applicationId") Integer applicationId){
        Boolean modifyApplication = applicationService.modifyApplication(applicationId, application);
        if (modifyApplication){
            return CommonReturnType.create("修改成功");
        }else {
            return CommonReturnType.create("修改失败");
        }
    }
    /**
     * 取消申请
     */
    @DeleteMapping("/{applicationId}/{userId}")
    public CommonReturnType  cancelApplication(@PathVariable Integer applicationId,
                                               @PathVariable Integer userId){
        applicationService.cancel(applicationId,userId);
        return CommonReturnType.create("已取消");
    }

}
