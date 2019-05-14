package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.pms.entity.ProductCategory;
import com.atguigu.gmall.pms.mapper.ProductCategoryMapper;
import com.atguigu.gmall.pms.service.ProductCategoryService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductCategoryParam;
import com.atguigu.gmall.vo.product.PmsProductCategoryWithChildrenItem;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.atguigu.gmall.constant.SysCacheConstant.CATEGORY_MENU_CACHE_KEY;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author lxx
 * @since 2019-05-09
 */
@Slf4j
@Service
@Component
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Override
    public PageInfoVo getPageInfoByCondition(Long parentId, Integer pageNum, Integer pageSize) {
        QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(parentId)){
            wrapper.eq("parent_id",parentId);
        }
        IPage<ProductCategory> page = new Page<>(pageNum,pageSize);
        productCategoryMapper.selectPage(page,wrapper);
        PageInfoVo pageInfoVo = new PageInfoVo(page.getTotal(),page.getPages(),pageSize.longValue(),
                page.getRecords(),page.getCurrent());
        return pageInfoVo;
    }

    @Override
    public List<PmsProductCategoryWithChildrenItem> getProductCategoryWithChildren(Integer i) {
        // 将分类菜单放入redis缓存
        Object cacheMenu = redisTemplate.opsForValue().get(CATEGORY_MENU_CACHE_KEY);
        List<PmsProductCategoryWithChildrenItem> list;
        if(cacheMenu == null){
            list = productCategoryMapper.getProductCategoryWithChildren(i);
            redisTemplate.opsForValue().set(CATEGORY_MENU_CACHE_KEY,list);
        }else{
            log.debug("菜单数据命中缓存");
            list = (List<PmsProductCategoryWithChildrenItem>)cacheMenu;
        }
        return list;
    }

    @Override
    public void saveProductCategory(PmsProductCategoryParam productCategoryParam) {
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(productCategoryParam,productCategory);
        int insert = productCategoryMapper.insert(productCategory);
        if(insert > 0){
            redisTemplate.opsForValue().set(CATEGORY_MENU_CACHE_KEY,null);
        }
    }

    @Override
    public void updateProductCategory(Long id, PmsProductCategoryParam productCategoryParam) {
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(productCategoryParam,productCategory);
        productCategory.setId(id);
        int update = productCategoryMapper.updateById(productCategory);
        if(update > 0){
            redisTemplate.opsForValue().set(CATEGORY_MENU_CACHE_KEY,null);
        }
    }

    @Override
    public void deleteProductCategoryById(Long id) {
        int delete = productCategoryMapper.deleteById(id);
        if(delete > 0){
            redisTemplate.opsForValue().set(CATEGORY_MENU_CACHE_KEY,null);
        }
    }
}
