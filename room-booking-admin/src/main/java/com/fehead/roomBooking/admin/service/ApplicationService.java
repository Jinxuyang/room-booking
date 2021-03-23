package com.fehead.roomBooking.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fehead.roomBooking.admin.entity.Application;
import com.fehead.roomBooking.admin.mapper.ApplicationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ApplicationService {
    private ApplicationMapper applicationMapper;
    @Autowired
    public ApplicationService(ApplicationMapper applicationMapper) {
        this.applicationMapper = applicationMapper;
    }
    /*
    增加application 检查是否与其他申请的时间重合
     */
    public Boolean addApplication(Application application){
        int insert = applicationMapper.insert(application);
        if (insert!=0){
            log.info("申请增加成功");
            return true;
        }else {
            return  false;
        }
    }
    //返回所有application的list  分页
    public List<Application> getAllApplication(int pageNum){
        //当前页 每页大小
        Page<Application> applicationPage=new Page<>(pageNum,5);
         applicationMapper.selectPage(applicationPage,null);
        return applicationPage.getRecords();
    }
    //按照id返回
    public Application getApplicationById(int id){
        return applicationMapper.selectById(id);
    }
    //按照条件返回信息相同的application的list
    public List<Application> getApplicationByMap(Map<String,String> map){
        QueryWrapper<Application> queryWrapper=new QueryWrapper<>();
        queryWrapper.allEq(map);
    return applicationMapper.selectList(queryWrapper);
    }
    //删除对应id
    public Boolean deleteById( Integer id){
        int delete = applicationMapper.deleteById(id);
        if (delete!=0){
            log.info("管理员删除了请求,id信息为"+id);
            return true;
        }else {
            return false;
        }
    }
    //id 修改application
    public Boolean changeApplication(Integer id, Application application) {
        application.setId(id);
        int update = applicationMapper.updateById(application);
        if (update!=0){
            log.info("管理员修改了请求,id为"+id);
            return true;
        }else {
            return false;
        }
    }
    //检查时间是否重复

}
