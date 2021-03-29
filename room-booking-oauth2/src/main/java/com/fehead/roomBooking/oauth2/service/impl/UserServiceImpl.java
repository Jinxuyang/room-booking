package com.fehead.roomBooking.oauth2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fehead.roomBooking.oauth2.entity.User;
import com.fehead.roomBooking.oauth2.mapper.UserMapper;
import com.fehead.roomBooking.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Verge
 * @since 2021-03-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isExist(String openid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openid);
        User user = userMapper.selectOne(wrapper);
        return user != null;
    }
}
