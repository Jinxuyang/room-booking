package com.fehead.roomBooking.oauth2.component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.roomBooking.oauth2.entity.Admin;
import com.fehead.roomBooking.oauth2.service.IAdminService;
import com.fehead.roomBooking.oauth2.service.impl.AdminServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 自定义登录逻辑
 * @Author Verge
 * @Date 2021/3/22 14:59
 * @Version 1.0
 */
@Component
public class UserService implements UserDetailsService {
    //@Autowired
    private IAdminService adminService = new AdminServiceImpl();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Admin admin = adminService.getOne(queryWrapper);
        if (admin == null) throw new UsernameNotFoundException("账号不存在");
        return new SecurityUser(admin);
    }
}
