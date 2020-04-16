package com.dl.mjapp.controller;

import cn.hutool.core.date.DateUtil;
import com.dl.common.model.entity.DLRoom;
import com.dl.common.model.entity.DlVisiteCode;
import com.dl.common.pojo.DlResponse;
import com.dl.mjapp.service.ISmscodeService;
import com.dl.mjapp.service.IVisitCodeService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("visitcode")
public class DlVisitCodeController extends BaseController {

	@Autowired
	private IVisitCodeService dlVisitCodeService;
	
	@Autowired
	private ISmscodeService smscodeService;
	
	@GetMapping(value="list")
	@ApiOperation("访客码生成记录")
	public DlResponse list(@RequestParam("phone")String phone) {
		List<DlVisiteCode> list = dlVisitCodeService.findAll(phone);
		return DlResponse.ok("操作成功", list);
	}	
	
	@PostMapping("applyVisitCode")
	@ApiOperation("applyVisitCode")
	public DlResponse applyVisitCode(DlVisiteCode code) {
		DlVisiteCode visiteCode = dlVisitCodeService.generateVisitCode(code);
		if (visiteCode.getPushed() == 1){
			smscodeService.sendVerifyCode(visiteCode.getVPhone());
		}
		return DlResponse.ok("操作成功", visiteCode);
	}

}
