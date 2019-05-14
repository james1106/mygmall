package com.atguigu.gmall.admin.oss.controller;


import com.atguigu.gmall.admin.oss.component.OssCompent;
import com.atguigu.gmall.admin.oss.component.OssPolicyResult;
import com.atguigu.gmall.to.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Oss相关操作接口
 */
@CrossOrigin
@Controller
@Api(tags = "OssController",description = "Oss管理")
@RequestMapping("/aliyun/oss")
public class OssController {
	@Autowired
	private OssCompent ossComponent;

	@ApiOperation(value = "oss上传签名生成")
	@GetMapping(value = "/policy")
	@ResponseBody
	public Object policy() {
		OssPolicyResult policy = ossComponent.policy();
		return new CommonResult().success(policy);
	}

}
