package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.ProductAttribute;
import com.atguigu.gmall.vo.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductAttributeParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务类
 * </p>
 *
 * @author lxx
 * @since 2019-05-09
 */
public interface ProductAttributeService extends IService<ProductAttribute> {

    PageInfoVo getCategoryAttributes(Long cid, Integer type, Integer pageNum, Integer pageSize);

    void saveProductAttribute(PmsProductAttributeParam productAttributeParam);

    ProductAttribute getProductAttributeById(Long id);

    void deleteBatchByIds(List<Long> ids);

    void updateProductAttributeById(Long id, PmsProductAttributeParam productAttributeParam);
}
