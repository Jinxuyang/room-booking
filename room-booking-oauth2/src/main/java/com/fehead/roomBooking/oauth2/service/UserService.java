package com.fehead.roomBooking.oauth2.service;

import com.fehead.roomBooking.oauth2.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Verge
 * @since 2021-03-26
 */
public interface UserService extends IService<User> {
    boolean isExist(String openid);
}
