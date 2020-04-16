package com.dl.mjapp.controller;

import com.dl.common.model.entity.DLArea;
import com.dl.common.pojo.DlResponse;
import com.dl.common.utils.QueryUtil;
import com.dl.mjapp.service.IAreaService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="area")
public class AreaController extends BaseController  {

	@Autowired
	private final IAreaService areaService;

	@GetMapping(value = "/list")
	@ApiOperation("get All DLArea")
	public DlResponse list() {
		HashMap<String, String> map = this.getRequestParamMap();
		Specification<DLArea> spec = QueryUtil.buildSpec(DLArea.class, map);
		List<DLArea> list = areaService.findAll(spec);
		return DlResponse.ok(list);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation("get DLArea By id")
	public DlResponse get(@PathVariable("id")int id) {
		DLArea area = areaService.getById(id);
		return DlResponse.ok(area);
	}

}
