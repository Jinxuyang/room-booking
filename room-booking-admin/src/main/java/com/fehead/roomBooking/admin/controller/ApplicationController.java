package com.fehead.roomBooking.admin.controller;

import com.fehead.roomBooking.common.entity.Application;
import com.fehead.roomBooking.admin.service.ApplicationService;
import com.fehead.roomBooking.common.controller.BaseController;
import com.fehead.roomBooking.common.entity.ApplicationReturnType;
import com.fehead.roomBooking.common.response.CommonReturnType;
import com.fehead.roomBooking.common.validation.Create;
import com.fehead.roomBooking.common.validation.Update;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/applications")
public class ApplicationController extends BaseController {
    private ApplicationService applicationService;
    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * 获取所有申请
     * @return
     */
    @ApiOperation(value = "获取所有申请")
    @GetMapping
    public ApplicationReturnType getAllApplication(Integer pageNum){
        if (pageNum==null){
            throw new RuntimeException("pageNum参数缺失");
        }
        return   applicationService.getAllApplication(pageNum);

    }

    /**
     * 获取指定id申请
     * @param applicationId
     * @return
     */
    @ApiOperation(value = "获取指定id申请")
    @GetMapping("/{applicationId}")
    public CommonReturnType getApplication(@PathVariable("applicationId") Integer applicationId){
        Application application = applicationService.getApplicationById(applicationId);
        if (application == null) return CommonReturnType.create(null,"failed");
        else return CommonReturnType.create(application);
    }

    /**
     * 模糊查询
     * @param applicant
     * @return
     */
    @ApiOperation(value = "模糊查询申请")
    @GetMapping("/search")
    public CommonReturnType searchApplication(String applicant){
        Map<String,String> map=new HashMap();
        map.put("applicant",applicant);
        return CommonReturnType.create(applicationService.getApplicationByMap(map));

    }
    /**
    新增申请
     */
    @ApiOperation(value = "新增申请")
    @PostMapping
    public CommonReturnType addApplication(@Validated(Create.class) @RequestBody Application application, BindingResult result){
        if (result.hasErrors()){
            throw new RuntimeException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        Boolean addApplication = applicationService.addApplication(application);
        if (addApplication){
          return CommonReturnType.create("success");
        }
        return CommonReturnType.create("fail");
    }
    /**
    修改指定id申请
     */
    @ApiOperation(value = "修改指定id申请")
    @PutMapping("/{applicationId}")
    public CommonReturnType modifyApplication(@PathVariable("applicationId") Integer applicationId,
                                              @Validated(Update.class) @RequestBody Application application){
        Boolean changeApplication = applicationService.modifyApplication(applicationId, application);
        if (changeApplication){
            return CommonReturnType.create("success");
        }
        return CommonReturnType.create("fail");
    }
    /**
    删除指定id申请
     */
    @ApiOperation(value =" 删除指定id申请" )
    @DeleteMapping("/{applicationId}")
    public CommonReturnType deleteApplication(@PathVariable("applicationId") Integer applicationId){
        Boolean delete = applicationService.deleteById(applicationId);
        if (delete){
            return CommonReturnType.create("success");
        }
        return CommonReturnType.create("fail");
    }
}
