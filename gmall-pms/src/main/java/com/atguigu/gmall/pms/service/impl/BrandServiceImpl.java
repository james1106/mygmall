package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.pms.entity.Brand;
import com.atguigu.gmall.pms.mapper.BrandMapper;
import com.atguigu.gmall.pms.service.BrandService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsBrandParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author lxx
 * @since 2019-05-09
 */
@Service
@Component
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
    BrandMapper brandMapper;

    @Override
    public PageInfoVo getBrandPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        QueryWrapper<Brand> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)){
            wrapper.like("name",keyword);
        }
        IPage<Brand> page = new Page<>(pageNum,pageSize);
        brandMapper.selectPage(page,wrapper);
        PageInfoVo pageInfoVo = new PageInfoVo(page.getTotal(),page.getPages(),page.getSize(),
        page.getRecords(),page.getCurrent());
        return pageInfoVo;
    }

    @Override
    public void saveBrand(PmsBrandParam pmsBrand) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(pmsBrand,brand);
        brandMapper.insert(brand);
    }

    @Override
    public Brand getBrandById(Long id) {
        Brand brand = brandMapper.selectById(id);
        return brand;
    }

    @Override
    public void updateBrandById(Long id, PmsBrandParam pmsBrandParam) {
        Brand brand = new Brand();
        brand.setId(id);
        BeanUtils.copyProperties(pmsBrandParam,brand);
        brandMapper.updateById(brand);
    }

    @Override
    public void deleteById(Long id) {
        brandMapper.deleteById(id);
    }

    @Override
    public void updateBrandShowStatus(List<Long> ids, Integer showStatus) {
        for (Long id : ids) {
            Brand brandById = getBrandById(id);
            brandById.setShowStatus(showStatus);
            brandMapper.update(brandById,null);
        }
    }
}
