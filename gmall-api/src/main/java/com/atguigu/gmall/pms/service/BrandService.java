package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.Brand;
import com.atguigu.gmall.vo.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsBrandParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author lxx
 * @since 2019-05-09
 */
public interface BrandService extends IService<Brand> {

    PageInfoVo getBrandPageInfo(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 添加品牌
     * @param pmsBrand
     */
    void saveBrand(PmsBrandParam pmsBrand);

    Brand getBrandById(Long id);

    void updateBrandById(Long id, PmsBrandParam pmsBrandParam);

    void deleteById(Long id);

    void updateBrandShowStatus(List<Long> ids, Integer showStatus);
}
