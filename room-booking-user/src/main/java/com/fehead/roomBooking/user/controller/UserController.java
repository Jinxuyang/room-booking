package com.fehead.roomBooking.user.controller;

import com.fehead.roomBooking.common.response.CommonReturnType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @PostMapping("/login")
    public CommonReturnType login(){
        return null;
    }
    @PostMapping
    public CommonReturnType logon(){
        return null;
    }
}
