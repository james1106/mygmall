package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.pms.entity.ProductAttribute;
import com.atguigu.gmall.pms.entity.ProductAttributeCategory;
import com.atguigu.gmall.pms.mapper.ProductAttributeCategoryMapper;
import com.atguigu.gmall.pms.mapper.ProductAttributeMapper;
import com.atguigu.gmall.pms.service.ProductAttributeCategoryService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductAttributeCategoryItem;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务实现类
 * </p>
 *
 * @author lxx
 * @since 2019-05-09
 */
@Service
@Component
public class ProductAttributeCategoryServiceImpl extends ServiceImpl<ProductAttributeCategoryMapper, ProductAttributeCategory> implements ProductAttributeCategoryService {

    @Autowired
    ProductAttributeCategoryMapper productAttributeCategoryMapper;

    @Autowired
    ProductAttributeMapper productAttributeMapper;

    @Override
    public PageInfoVo productAttributeCategoryPageInfo(Integer pageNum, Integer pageSize) {
        Page<ProductAttributeCategory> productAttributeCategoryPage = new Page<>(pageNum, pageSize);

        IPage<ProductAttributeCategory> page = productAttributeCategoryMapper.selectPage(productAttributeCategoryPage, null);

        return PageInfoVo.getVo(page,pageSize.longValue());
    }

    @Override
    public List<PmsProductAttributeCategoryItem> getPmsProductAttributeCategoryItemList() {
        List<PmsProductAttributeCategoryItem> list = new ArrayList<>();

        List<ProductAttributeCategory> listTemp = productAttributeCategoryMapper.selectList(null);
        for (ProductAttributeCategory productAttributeCategory : listTemp) {
            PmsProductAttributeCategoryItem pmsProductAttributeCategoryItem = new PmsProductAttributeCategoryItem();
            QueryWrapper<ProductAttribute> wrapper = new QueryWrapper<>();
            wrapper.eq("product_attribute_category_id",productAttributeCategory.getId());
            List<ProductAttribute> productAttributes = productAttributeMapper.selectList(wrapper);
            BeanUtils.copyProperties(productAttributeCategory,pmsProductAttributeCategoryItem);
            pmsProductAttributeCategoryItem.setProductAttributeList(productAttributes);
            list.add(pmsProductAttributeCategoryItem);
        }
        return list;
    }

    @Override
    public void saveProductAttributeCategory(String name) {
        ProductAttributeCategory productAttributeCategory = new ProductAttributeCategory();
        productAttributeCategory.setName(name);
        productAttributeCategoryMapper.insert(productAttributeCategory);
    }

    @Override
    public void updateNameById(Long id, String name) {
        ProductAttributeCategory productAttributeCategory = productAttributeCategoryMapper.selectById(id);
        productAttributeCategory.setName(name);
        productAttributeCategoryMapper.updateById(productAttributeCategory);
    }

    @Override
    public void deleteById(Long id) {
        productAttributeCategoryMapper.deleteById(id);
    }
}
