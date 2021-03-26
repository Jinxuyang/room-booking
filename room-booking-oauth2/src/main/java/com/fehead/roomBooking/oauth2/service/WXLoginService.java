package com.fehead.roomBooking.oauth2.service;

/**
 * @Author Verge
 * @Date 2021/3/26 10:28
 * @Version 1.0
 */
public interface WXLoginService {
    String login(String code) throws Exception;
}
