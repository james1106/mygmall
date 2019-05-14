package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.pms.entity.ProductAttribute;
import com.atguigu.gmall.pms.mapper.ProductAttributeMapper;
import com.atguigu.gmall.pms.service.ProductAttributeService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductAttributeParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author lxx
 * @since 2019-05-09
 */
@Service
@Component
public class ProductAttributeServiceImpl extends ServiceImpl<ProductAttributeMapper, ProductAttribute> implements ProductAttributeService {

    @Autowired
    ProductAttributeMapper productAttributeMapper;

    @Override
    public PageInfoVo getCategoryAttributes(Long cid, Integer type, Integer pageNum, Integer pageSize) {
        QueryWrapper<ProductAttribute> wrapper = new QueryWrapper();
        wrapper.eq("product_attribute_category_id",cid);
        wrapper.eq("type",type);
        IPage<ProductAttribute> page = productAttributeMapper.selectPage(new Page<ProductAttribute>(pageNum, pageSize), wrapper);
        return PageInfoVo.getVo(page,pageSize.longValue());
    }

    @Override
    public void saveProductAttribute(PmsProductAttributeParam productAttributeParam) {
        ProductAttribute productAttribute = new ProductAttribute();
        BeanUtils.copyProperties(productAttributeParam,productAttribute);
        productAttributeMapper.insert(productAttribute);
    }

    @Override
    public ProductAttribute getProductAttributeById(Long id) {
        ProductAttribute productAttribute = productAttributeMapper.selectById(id);
        return productAttribute;
    }

    @Override
    public void deleteBatchByIds(List<Long> ids) {
        productAttributeMapper.deleteBatchIds(ids);
    }

    @Override
    public void updateProductAttributeById(Long id, PmsProductAttributeParam productAttributeParam) {
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setId(id);
        BeanUtils.copyProperties(productAttributeParam,productAttribute);
        productAttributeMapper.updateById(productAttribute);
    }

}
