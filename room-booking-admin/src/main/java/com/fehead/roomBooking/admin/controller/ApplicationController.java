package com.fehead.roomBooking.admin.controller;

import com.fehead.roomBooking.admin.entity.Application;
import com.fehead.roomBooking.admin.mapper.ApplicationMapper;
import com.fehead.roomBooking.admin.service.ApplicationService;
import com.fehead.roomBooking.common.controller.BaseController;
import com.fehead.roomBooking.common.response.CommonReturnType;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/applications")
public class ApplicationController extends BaseController {
    private ApplicationService applicationService;
    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /*
        获取所有申请
         */
    @ApiOperation(value = "get请求调用示例", notes = "invokePost说明")
    @GetMapping
    public CommonReturnType getAllApplication(Integer pageNum){
        CommonReturnType returnType=new CommonReturnType();
        returnType.setData(applicationService.getAllApplication(pageNum));
        returnType.setStatus("200");
        return returnType;
    }
    /*
    获取指定id申请
     */
    @GetMapping("/{applicationId}")
    public CommonReturnType getApplication(@PathVariable("applicationId") Integer applicationId){
        CommonReturnType returnType=new CommonReturnType();

        Application applicationById = applicationService.getApplicationById(applicationId);
        if (applicationById!=null){
            returnType.setData(applicationById);
            returnType.setStatus("200");
        }
        return returnType;
    }
    /*
    新增申请
     */
    @PostMapping
    public CommonReturnType addApplication(@RequestBody Application application){
        CommonReturnType returnType=new CommonReturnType();
        Boolean addApplication = applicationService.addApplication(application);
        if (addApplication){
            returnType.setStatus("200");
            returnType.setData("新增成功");
        }
        return returnType;
    }
    /*
    修改指定id申请
     */
    @PutMapping("/{applicationId}")
    public CommonReturnType changeApplication(@PathVariable("applicationId") Integer applicationId,
                                              @RequestBody Application application){
        CommonReturnType returnType=new CommonReturnType();
        Boolean changeApplication = applicationService.changeApplication(applicationId, application);
        if (changeApplication){
            returnType.setStatus("200");
            returnType.setData("application修改成功");
        }
        return returnType;
    }
    /*
    删除指定id申请
     */
    @DeleteMapping("/{applicationId}")
    public CommonReturnType deleteApplication(@PathVariable("applicationId") Integer applicationId){
        CommonReturnType returnType=new CommonReturnType();
        Boolean delete = applicationService.deleteById(applicationId);
        if (delete){
            returnType.setStatus("200");
            returnType.setData("application修改成功");
        }
        return returnType;
    }
}
