package com.fehead.roomBooking.oauth2.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author Verge
 * @Date 2021/3/31 11:04
 * @Version 1.0
 */
@Data
public class SecurityUser implements UserDetails {
    private String username;

    private String password;

    private Collection<SimpleGrantedAuthority> authorities;

    public SecurityUser() {
    }

    public SecurityUser(Admin admin) {
        this.username = admin.getUsername();
        this.password = admin.getPassword();
        if (admin.getRole() != null){
            authorities = new ArrayList<>();
            admin.getRole().forEach(item -> authorities.add(new SimpleGrantedAuthority(item)));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
