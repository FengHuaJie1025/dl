package com.dl.mjweb.controller;

import com.dl.common.model.entity.DLGate;
import com.dl.common.pojo.DlResponse;
import com.dl.common.utils.QueryUtil;
import com.dl.mjweb.aop.OperationLogAnn;
import com.dl.mjweb.service.IGateService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
		List<DLGate> list = gateService.findAll();
		return DlResponse.ok(list);
	}
	
	@GetMapping(value="page")
	@ApiOperation("page DLGate")
	public DlResponse page(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize) {
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		HashMap<String, String> map = this.getRequestParamMap();
		Specification<DLGate> spec = QueryUtil.buildSpec(DLGate.class,map,"name");
		Page page=  gateService.page(spec,pageable);
		return DlResponse.ok(page);
	}

	@PostMapping
	@OperationLogAnn("add DLGate")
	@ApiOperation("add DLGate")
	 public DlResponse add(@RequestBody DLGate bean, HttpServletRequest request) {
		gateService.save(bean);
		return DlResponse.ok(bean);
    }

	@PutMapping(value = "/{id}")
	@OperationLogAnn("update DLGate")
	@ApiOperation("update DLGate By id")
	public DlResponse update(@PathVariable("id")int id,@RequestBody DLGate bean,HttpServletRequest request) {
		bean.setId(id);
		gateService.update(bean);
		return DlResponse.ok(bean);
    }
	
	@GetMapping(value = "/{id}")
	@ApiOperation("get DLGate By id")
	public DlResponse get(@PathVariable("id")int id) {
		DLGate gate = gateService.getById(id);
		return DlResponse.ok(gate);
	}
	
	@DeleteMapping(value = "/{id}")
	@OperationLogAnn("delete DLGate")
	@ApiOperation("delete DLGate By id")
	public DlResponse delete(@PathVariable("id")int id) {
		gateService.delete(id);
		return DlResponse.ok(null);
	}

//	private Specification<DLGate> buildSpecByQuery() {
//		HashMap<String,String> map = getRequestParamMap();
//
//		return (Specification<DLGate>) (root, query, criteriaBuilder) -> {
//			List<Predicate> predicates=new ArrayList<>();
//			if (StringUtils.isNotBlank(map.get("keyword"))) {
//				String keyword = "%"+map.get("keyword").trim()+"%";
//				Predicate question = criteriaBuilder.like(root.get("name"), keyword);
//				Predicate answer = criteriaBuilder.like(root.get("phone"), keyword);
//				predicates.add(criteriaBuilder.or(question,answer));
//			}
//			if (StringUtils.isNotBlank(map.get("start")) && StringUtils.isNotBlank(map.get("end"))) {
//				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//				Date startDate = null;
//				Date endDate = null;
//				try {
//					startDate = simpleDateFormat.parse(map.get("start"));
//					endDate = simpleDateFormat.parse(map.get("end"));
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				predicates.add(criteriaBuilder.between(root.<Date>get("createTime"),startDate, endDate));
//			}
//			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//		};
//
//	}

}
