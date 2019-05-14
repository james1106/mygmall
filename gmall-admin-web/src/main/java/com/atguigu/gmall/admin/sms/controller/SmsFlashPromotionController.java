package com.atguigu.gmall.admin.sms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.sms.entity.FlashPromotion;
import com.atguigu.gmall.sms.service.FlashPromotionService;
import com.atguigu.gmall.to.CommonResult;
import com.atguigu.gmall.vo.PageInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Api(tags = "SmsFlashPromotionController", description = "限时购活动管理")
@Controller
@RequestMapping("/flash")
public class SmsFlashPromotionController {

    @Reference
    FlashPromotionService flashPromotionService;

    @ApiOperation("添加一个活动")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody FlashPromotion flashPromotion){
        flashPromotionService.saveFlashPromotion(flashPromotion);
        return new CommonResult().success(null);
    }

    @ApiOperation("根据活动名称分页查询")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Object getItem(@RequestParam(value = "keyword",required = false) String keyword,
                          @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                          @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum){
        PageInfoVo pageInfoVo = flashPromotionService.listFlashPromotionForPage(keyword,pageNum,pageSize);
        return new CommonResult().success(pageInfoVo);
    }

}
