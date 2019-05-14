package com.atguigu.gmall.sms.service;

import com.atguigu.gmall.sms.entity.FlashPromotion;
import com.atguigu.gmall.vo.PageInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 限时购表 服务类
 * </p>
 *
 * @author lxx
 * @since 2019-05-09
 */
public interface FlashPromotionService extends IService<FlashPromotion> {

    PageInfoVo listFlashPromotionForPage(String keyword, Integer pageNum, Integer pageSize);

    void saveFlashPromotion(FlashPromotion flashPromotion);
}
