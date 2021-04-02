package com.fehead.roomBooking.common.entity;

import com.fehead.roomBooking.common.response.CommonReturnType;
import com.fehead.roomBooking.common.response.FeheadResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lmwis
 * @description:统一返回类型
 * @date 2019-08-28 20:33
 * @Version 1.0
 */
@Data
@ApiModel(value = "api接口通用业务常用返回类型",parent = FeheadResponse.class)
public class ApplicationReturnType extends FeheadResponse {
    // 返回请求处理结果
    @ApiModelProperty(value = "请求状态",dataType = "String")
    private String status;
private Long pageTotal;
private  Long total;

    public static CommonReturnType create(Object result) {
        return CommonReturnType.create(result, "success");
    }

    public static CommonReturnType create(Object result, String status) {
        CommonReturnType type = new CommonReturnType();
        type.setData(result);
        type.setStatus(status);

        return type;
    }

}
