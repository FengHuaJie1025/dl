package com.dl.mjapp.controller;

import com.dl.common.pojo.DlResponse;
import com.dl.mjapp.service.ISmscodeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class DlSmsCodeController extends BaseController {

	@Autowired
	private ISmscodeService dlSmscodeService;

	// 短信验证码接口
	@PostMapping(value = "smscode")
	@ApiOperation("sendVerifyCode")
	public DlResponse sendVerifyCode(@RequestParam("phone")String phone) throws Exception {
		Boolean b = dlSmscodeService.sendVerifyCode(phone);
		if (b){
			return DlResponse.ok("短信发送成功");
		}
		return DlResponse.ok("短信发送失败");
	}
}
