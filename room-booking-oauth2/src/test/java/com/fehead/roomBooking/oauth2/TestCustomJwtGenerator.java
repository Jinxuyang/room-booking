package com.fehead.roomBooking.oauth2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fehead.roomBooking.oauth2.utils.JwtUtils;
import com.nimbusds.jose.JOSEException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author Verge
 * @Date 2021/3/26 18:30
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCustomJwtGenerator {
    @Autowired
    JwtUtils jwtUtils;


    @Test
    public void test() throws JsonProcessingException, JOSEException {
        System.out.println(jwtUtils.generateToken());
    }
}
