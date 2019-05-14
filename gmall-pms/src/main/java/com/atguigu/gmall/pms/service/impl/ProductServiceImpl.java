package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.mapper.*;
import com.atguigu.gmall.pms.service.ProductService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.atguigu.gmall.vo.product.PmsProductParam;
import com.atguigu.gmall.vo.product.PmsProductQueryParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author lxx
 * @since 2019-05-09
 */
@Slf4j
@Service
@Component
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductAttributeValueMapper productAttributeValueMapper;

    @Autowired
    ProductFullReductionMapper productFullReductionMapper;

    @Autowired
    ProductLadderMapper productLadderMapper;

    @Autowired
    SkuStockMapper skuStockMapper;

    // 当前线程共享同样的数据，只能存一个，当前业务存product.getId()
    ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    // ThreadLocal的原理，以当前线程(在本线程内具有唯一性)为key
    private Map<Thread,Long> map = new HashMap<>();

    @Override
    public PageInfoVo getPageInfo(PmsProductQueryParam p) {

        QueryWrapper<Product> wrapper = new QueryWrapper<>();

        Long brandId = p.getBrandId();
        Long productCategoryId = p.getProductCategoryId();
        String productSn = p.getProductSn();
        Integer publishStatus = p.getPublishStatus();
        Integer verifyStatus = p.getVerifyStatus();
        String keyword = p.getKeyword();

        if(brandId!=null){
            wrapper.eq("brand_id",brandId);
        }
        if (productCategoryId!=null){
            wrapper.eq("product_category_id",productCategoryId);
        }
        if (publishStatus !=null){
            wrapper.eq("publish_status",publishStatus);
        }
        if (verifyStatus!=null){
            wrapper.eq("verify_status",verifyStatus);
        }
        if (!StringUtils.isEmpty(productSn)){
            wrapper.like("product_sn",productSn);
        }
        if (!StringUtils.isEmpty(keyword)){
            wrapper.like("name",keyword);
        }

        IPage<Product> page = new Page<>(p.getPageNum(),p.getPageSize());
        productMapper.selectPage(page,wrapper);
        PageInfoVo pageInfoVo = new PageInfoVo(page.getTotal(), page.getPages(), page.getSize(), page.getRecords(), page.getCurrent());
        return pageInfoVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveProduct(PmsProductParam productParam) {
        ProductServiceImpl proxy = (ProductServiceImpl)AopContext.currentProxy();
        //1) pms_product 保存商品基本信息
        proxy.saveBaseInfo(productParam);
        //2) pms_product_attribute_value 保存商品对应所有属性值
        proxy.saveProductAttributeValueList(productParam);
        //3) pms_product_full_reduction 保存商品的满减信息
        proxy.saveProductFullReductionList(productParam);
        //4) pms_product_ladder 打折表
        proxy.saveProductLadderList(productParam);
        //5) pms_sku_stock 库存表
        proxy.saveSkuStockList(productParam);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveSkuStockList(PmsProductParam productParam) {
        List<SkuStock> skuStockList = productParam.getSkuStockList();
        for (int i = 1; i<=skuStockList.size(); i++) {
            SkuStock skuStock = skuStockList.get(i-1);
            if(StringUtils.isEmpty(skuStock.getSkuCode())){
                //skuCode必须有  1_1  1_2 1_3 1_4
                //生成规则  商品id_sku自增id
                skuStock.setSkuCode(threadLocal.get()+"_"+i);
            }
            skuStock.setProductId(threadLocal.get());
            skuStockMapper.insert(skuStock);
        }
        int i = 10 / 0;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProductLadderList(PmsProductParam productParam) {
        List<ProductLadder> productLadderList = productParam.getProductLadderList();
        for (ProductLadder productLadder : productLadderList) {
            productLadder.setProductId(threadLocal.get());
            productLadderMapper.insert(productLadder);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProductFullReductionList(PmsProductParam productParam) {
        List<ProductFullReduction> productFullReductionList = productParam.getProductFullReductionList();
        for (ProductFullReduction productFullReduction : productFullReductionList) {
            productFullReduction.setProductId(threadLocal.get());
            productFullReductionMapper.insert(productFullReduction);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProductAttributeValueList(PmsProductParam productParam) {
        List<ProductAttributeValue> productAttributeValueList = productParam.getProductAttributeValueList();
        for (ProductAttributeValue productAttributeValue : productAttributeValueList) {
            productAttributeValue.setProductId(threadLocal.get());
            productAttributeValueMapper.insert(productAttributeValue);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveBaseInfo(PmsProductParam productParam) {
        Product product = new Product();
        BeanUtils.copyProperties(productParam,product);
        productMapper.insert(product);
        // mybatis-plus能自动获取到这个数据的自增id
        log.debug("当前线程的id：{}",product.getId());
        threadLocal.set(product.getId());

        map.put(Thread.currentThread(),product.getId());
        log.debug("当前线程的id：{}和name：{}",Thread.currentThread().getId(),Thread.currentThread().getName());
    }
}
