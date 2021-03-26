package com.fehead.roomBooking.user.controller;

import com.fehead.roomBooking.common.controller.BaseController;
import com.fehead.roomBooking.common.entity.Application;
import com.fehead.roomBooking.common.response.CommonReturnType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController extends BaseController {
    /*
新增申请
 */
    @PostMapping
    public CommonReturnType addApplication(@RequestBody Application application){
        return null;
    }
    /*
    修改申请modify
    */
    @PutMapping("{applicationId}")
    public CommonReturnType modifyApplication(@RequestBody Application application,
                                              @PathVariable("applicationId") String applicationId){
        return null;
    }
}
