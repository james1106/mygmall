package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.ProductCategory;
import com.atguigu.gmall.vo.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductCategoryParam;
import com.atguigu.gmall.vo.product.PmsProductCategoryWithChildrenItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author lxx
 * @since 2019-05-09
 */
public interface ProductCategoryService extends IService<ProductCategory> {

    PageInfoVo getPageInfoByCondition(Long parentId, Integer pageNum, Integer pageSize);

    List<PmsProductCategoryWithChildrenItem> getProductCategoryWithChildren(Integer i);

    void saveProductCategory(PmsProductCategoryParam productCategoryParam);

    void updateProductCategory(Long id, PmsProductCategoryParam productCategoryParam);

    void deleteProductCategoryById(Long id);
}
