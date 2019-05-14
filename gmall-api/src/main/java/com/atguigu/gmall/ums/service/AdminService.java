package com.atguigu.gmall.ums.service;

import com.atguigu.gmall.ums.entity.Admin;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author lxx
 * @since 2019-05-09
 */
public interface AdminService extends IService<Admin> {

    Admin login(String username, String password);

    Admin getAdminInfo(String username);

}
