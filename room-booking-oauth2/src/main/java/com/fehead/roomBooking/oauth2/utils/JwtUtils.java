package com.fehead.roomBooking.oauth2.utils;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fehead.roomBooking.oauth2.dto.PayloadDto;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.util.UUID;

/**
 * @Author Verge
 * @Date 2021/3/26 17:37
 * @Version 1.0
 */
@Component
public class JwtUtils {
    @Autowired
    private KeyPair keyPair;

    public String generateToken(int userId) throws JOSEException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String payloadStr = objectMapper.writeValueAsString(getDefaultPayload(userId));
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build();
        Payload payload = new Payload(payloadStr);
        JWSObject jwsObject = new JWSObject(jwsHeader,payload);
        JWSSigner jwsSigner = new RSASSASigner(keyPair.getPrivate());
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    public PayloadDto getDefaultPayload(int userId){
        return PayloadDto.builder()
                .jti(UUID.randomUUID().toString())
                .authorities(CollUtil.toList("user"))
                .client_id("user")
                .exp((System.currentTimeMillis() / 1000) + 36000)
                .scope(CollUtil.toList("all"))
                .user_id(userId)
                .build();
    }

}
