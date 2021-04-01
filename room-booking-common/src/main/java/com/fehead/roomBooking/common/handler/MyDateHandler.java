package com.fehead.roomBooking.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * 自动填充绑定类
 */
@Component
@Slf4j
public class MyDateHandler implements MetaObjectHandler {
    //插入填充
    @Override
    public void insertFill(MetaObject metaObject) {
    //log.info("插入填充");
    //this.setFieldValByName("creatTime",Calendar.getInstance().getTime(),metaObject);
    }
    //更新填充
    @Override
    public void updateFill(MetaObject metaObject) {
      //  log.info("更新填充"+new Date());
       this.setFieldValByName("application_stamp",new Date().getTime(),metaObject);
        this.setFieldValByName("status",0,metaObject);
    }
}
