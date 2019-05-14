package com.atguigu.gmall.ums.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.ums.entity.Admin;
import com.atguigu.gmall.ums.mapper.AdminMapper;
import com.atguigu.gmall.ums.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author lxx
 * @since 2019-05-09
 */
@Component
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String username, String password) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>().eq("username", username).eq("password", DigestUtils.md5DigestAsHex(password.getBytes()));
        Admin admin = adminMapper.selectOne(wrapper);
        return admin;
    }

    @Override
    public Admin getAdminInfo(String username) {
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username));
        return admin;
    }
}
