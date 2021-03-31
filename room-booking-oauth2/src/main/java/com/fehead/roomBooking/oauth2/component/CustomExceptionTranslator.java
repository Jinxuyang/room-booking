package com.fehead.roomBooking.oauth2.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * @Author Verge
 * @Date 2021/3/29 21:17
 * @Version 1.0
 */
@Component
@Slf4j
public class CustomExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        log.info("进入");
        return new ResponseEntity<>(OAuth2Exception.create("",e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
 