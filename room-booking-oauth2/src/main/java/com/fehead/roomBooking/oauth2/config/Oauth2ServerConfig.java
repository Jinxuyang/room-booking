package com.fehead.roomBooking.oauth2.config;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;

/**
 * 授权服务器配置
 * @Author Verge
 * @Date 2021/3/22 16:20
 * @Version 1.0
 */
@AllArgsConstructor
//@Configuration
//@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {
    /*@Autowired
    private final PasswordEncoder passwordEncoder;
    *//*@Autowired
    private AuthenticationManager authenticationManager;*//*
    @Autowired
    private final UserService userService;
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("admin")
                .secret(passwordEncoder.encode("123456"));

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
    }*/
}
