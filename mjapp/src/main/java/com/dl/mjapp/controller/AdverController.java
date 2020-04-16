package com.dl.mjapp.controller;

import com.dl.common.model.entity.DLAdver;
import com.dl.common.model.entity.DLAdverLog;
import com.dl.common.model.entity.DLArea;
import com.dl.common.model.entity.DlEquipment;
import com.dl.common.model.params.DLAdverLogParam;
import com.dl.common.pojo.DlResponse;
import com.dl.common.utils.QueryUtil;
import com.dl.mjapp.service.IAdverLogService;
import com.dl.mjapp.service.IAdverService;
import com.dl.mjapp.service.IApplyService;
import com.dl.mjapp.service.IEquipmentService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="adver")
public class AdverController extends BaseController  {

	@Autowired
	private final IAdverService adverService;

	@Autowired
	private final IAdverLogService adverLogService;

	@Autowired
	private final IEquipmentService equipmentService;

	@PostMapping(value = "/getAD")
	@ApiOperation("get Precision advertising")
	public DlResponse getAD(@RequestParam("userid") Integer userid,@RequestParam("pid") String pid) {
		DLArea area = equipmentService.getAreaByPid(pid);
		DLAdver adver = adverService.getAdverByAreaId(area.getId());
		if (adver == null){
			return DlResponse.ok();
		}
		return DlResponse.ok(adver);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation("get DLAdver By id")
	public DlResponse get(@PathVariable("id")int id) {
		DLAdver area = adverService.getById(id);
		return DlResponse.ok(area);
	}

	@PostMapping(value="/uploadADLog")
	@ApiOperation("uploadADLog")
	public DlResponse uploadADLog(DLAdverLog adverLog){
		adverLogService.save(adverLog);
		return DlResponse.ok();
	}

}
