package com.fehead.roomBooking.oauth2.service.impl;

import com.fehead.roomBooking.oauth2.entity.Admin;
import com.fehead.roomBooking.oauth2.mapper.AdminMapper;
import com.fehead.roomBooking.oauth2.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Verge
 * @since 2021-03-22
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
