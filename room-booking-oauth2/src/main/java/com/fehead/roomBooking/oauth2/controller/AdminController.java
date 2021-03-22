package com.fehead.roomBooking.oauth2.controller;


import com.fehead.roomBooking.common.response.CommonReturnType;
import com.fehead.roomBooking.oauth2.entity.Admin;
import com.fehead.roomBooking.oauth2.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Verge
 * @since 2021-03-22
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping
    public CommonReturnType insert(@RequestBody Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminService.save(admin);
        return CommonReturnType.create("ok");
    }
}

