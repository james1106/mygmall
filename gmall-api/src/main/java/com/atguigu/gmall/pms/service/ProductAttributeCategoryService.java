package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.ProductAttributeCategory;
import com.atguigu.gmall.vo.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductAttributeCategoryItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务类
 * </p>
 *
 * @author lxx
 * @since 2019-05-09
 */
public interface ProductAttributeCategoryService extends IService<ProductAttributeCategory> {

    /**
     * 分页查询所有属性分类
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfoVo productAttributeCategoryPageInfo(Integer pageNum, Integer pageSize);

    /**
     * 获取筛选属性分类集合
     * @return
     */
    List<PmsProductAttributeCategoryItem> getPmsProductAttributeCategoryItemList();

    void saveProductAttributeCategory(String name);

    void updateNameById(Long id, String name);

    void deleteById(Long id);
}
