package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.Product;
import com.atguigu.gmall.vo.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductParam;
import com.atguigu.gmall.vo.product.PmsProductQueryParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author lxx
 * @since 2019-05-09
 */
public interface ProductService extends IService<Product> {

    /**
     * 根据复杂查询条件返回分页
     * @param productQueryParam
     * @return
     */

    PageInfoVo getPageInfo(PmsProductQueryParam productQueryParam);

    /**
     *  保存商品数据
     * @param productParam
     */
    void saveProduct(PmsProductParam productParam);
}
