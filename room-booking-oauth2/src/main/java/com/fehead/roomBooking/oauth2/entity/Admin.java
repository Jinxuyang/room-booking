package com.fehead.roomBooking.oauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Author Verge
 * @Date 2021/3/31 10:58
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class Admin {
    private String username;
    private String password;
    private List<String> role;
}
