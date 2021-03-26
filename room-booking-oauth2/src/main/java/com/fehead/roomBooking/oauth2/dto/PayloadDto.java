package com.fehead.roomBooking.oauth2.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author Verge
 * @Date 2021/3/26 18:11
 * @Version 1.0
 */
@Data
@Builder
public class PayloadDto {
    private Long exp;

    private String jti;

    private String user_name;

    private List<String> authorities;

    private String client_id;

    private List<String> scope;

}
