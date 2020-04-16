package com.dl.mjapp.controller;

import com.dl.common.model.entity.DLGate;
import com.dl.common.pojo.DlResponse;
import com.dl.common.utils.QueryUtil;
import com.dl.mjapp.service.IGateService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="gate")
public class GateController extends BaseController  {

	@Autowired
	private final IGateService gateService;

	@GetMapping(value = "/list")
	@ApiOperation("get All DLGate")
	public DlResponse list() {
		HashMap<String, String> map = this.getRequestParamMap();
		Specification<DLGate> spec = QueryUtil.buildSpec(DLGate.class, map,"areaid","name");
		List<DLGate> list = gateService.findAll(spec);
		return DlResponse.ok(list);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation("get DLGate By id")
	public DlResponse get(@PathVariable("id")int id) {
		DLGate gate = gateService.getById(id);
		return DlResponse.ok(gate);
	}

}
