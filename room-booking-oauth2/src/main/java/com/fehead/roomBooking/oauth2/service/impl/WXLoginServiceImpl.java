package com.fehead.roomBooking.oauth2.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fehead.roomBooking.oauth2.entity.User;
import com.fehead.roomBooking.oauth2.service.UserService;
import com.fehead.roomBooking.oauth2.service.WXLoginService;
import com.fehead.roomBooking.oauth2.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author Verge
 * @Date 2021/3/26 10:31
 * @Version 1.0
 */
@Service
public class WXLoginServiceImpl implements WXLoginService {
    @Value("${weixin.appid}")
    private String APPID ;
    @Value("${weixin.secret}")
    private String SECRET;
    private static final String GRANT_TYPE = "authorization_code";
    private static final String URL = " https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;
    @Override
    public String login(String code) throws Exception {

        HashMap<String,Object> param = new HashMap<>();
        param.put("appid",APPID);
        param.put("secret",SECRET);
        param.put("grant_type",GRANT_TYPE);
        param.put("js_code",code);

        String response = HttpUtil.get(URL,param);

        JSONObject jsonObject = JSONUtil.parseObj(response);
        if (jsonObject.get("session_key") == null) throw new Exception("微信登陆失败");

        String openid = jsonObject.getStr("openid");
        if (!userService.isExist(openid)){
            User user = new User();
            user.setOpenId(openid);
            userService.save(user);
        }
        return jwtUtils.generateToken();

    }
}
