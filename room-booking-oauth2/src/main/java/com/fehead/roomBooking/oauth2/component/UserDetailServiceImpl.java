package com.fehead.roomBooking.oauth2.component;

import cn.hutool.core.collection.CollUtil;
import com.fehead.roomBooking.oauth2.entity.Admin;
import com.fehead.roomBooking.oauth2.entity.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Verge
 * @Date 2021/3/31 10:56
 * @Version 1.0
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {
    private List<Admin> userList;

    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @PostConstruct
    public void initData() {
        userList = new ArrayList<>();
        String password = passwordEncoder.encode("admin");
        userList = new ArrayList<>();
        userList.add(new Admin("admin", password,CollUtil.toList("admin")));
    }
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Admin> findUserList = userList.stream().filter(item -> item.getUsername().equals(username)).collect(Collectors.toList());
        if (CollUtil.isEmpty(findUserList)) {
            throw new UsernameNotFoundException("无此用户");
        }
        SecurityUser securityUser = new SecurityUser(findUserList.get(0));
        /*if (!securityUser.isEnabled()) {
            throw new DisabledException("");
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException("");
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }*/
        return securityUser;
    }
}
