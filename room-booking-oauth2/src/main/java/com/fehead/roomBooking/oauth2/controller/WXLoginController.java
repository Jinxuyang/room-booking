package com.fehead.roomBooking.oauth2.controller;

import com.fehead.roomBooking.common.controller.BaseController;
import com.fehead.roomBooking.common.response.CommonReturnType;
import com.fehead.roomBooking.oauth2.service.WXLoginService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Verge
 * @Date 2021/3/25 21:12
 * @Version 1.0
 */
@RestController
public class WXLoginController extends BaseController {
    @Autowired
    private WXLoginService wxLoginService;
    @PostMapping("/user/login")
    public CommonReturnType wxlogin(@RequestParam String code) throws Exception {
        String token = wxLoginService.login(code);
        if (!StringUtils.isEmpty(token)) return CommonReturnType.create(token);
        else return CommonReturnType.create("生成token失败","failed");
    }
}
