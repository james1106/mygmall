package com.atguigu.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.sms.entity.FlashPromotion;
import com.atguigu.gmall.sms.mapper.FlashPromotionMapper;
import com.atguigu.gmall.sms.service.FlashPromotionService;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 限时购表 服务实现类
 * </p>
 *
 * @author lxx
 * @since 2019-05-09
 */
@Slf4j
@Service
@Component
public class FlashPromotionServiceImpl extends ServiceImpl<FlashPromotionMapper, FlashPromotion> implements FlashPromotionService {

    @Autowired
    FlashPromotionMapper flashPromotionMapper;

    @Override
    public PageInfoVo listFlashPromotionForPage(String keyword, Integer pageNum, Integer pageSize) {
        IPage<FlashPromotion> page = new Page<>(pageNum,pageSize);
        QueryWrapper<FlashPromotion> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(keyword)) {
            wrapper.like("title", keyword);
        }
        flashPromotionMapper.selectPage(page, wrapper);
        PageInfoVo vo = PageInfoVo.getVo(page, pageSize.longValue());
        return vo;
    }

    @Override
    public void saveFlashPromotion(FlashPromotion flashPromotion) {
        flashPromotionMapper.insert(flashPromotion);
    }
}
