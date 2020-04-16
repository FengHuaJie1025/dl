package com.dl.mjweb.controller;

import com.dl.common.model.entity.DlEquipment;
import com.dl.common.pojo.DlResponse;
import com.dl.common.utils.QueryUtil;
import com.dl.mjweb.aop.OperationLogAnn;
import com.dl.mjweb.service.IEquipmentService;
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
@RequestMapping(value="equipment")
public class EquipmentController extends BaseController  {

	@Autowired
	private final IEquipmentService equipmentService;

	@GetMapping(value = "/list")
	@ApiOperation("get All DlEquipment")
	public DlResponse list() {
		List<DlEquipment> list = equipmentService.findAll();
		return DlResponse.ok(list);
	}
	
	@GetMapping(value="page")
	@ApiOperation("page DlEquipment")
	public DlResponse page(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize) {
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		HashMap<String, String> map = this.getRequestParamMap();
		Specification<DlEquipment> spec = QueryUtil.buildSpec(DlEquipment.class,map,"pid","devicename");
		Page page=  equipmentService.page(spec,pageable);
		return DlResponse.ok(page);
	}

	@PostMapping
	@OperationLogAnn("add DlEquipment")
	@ApiOperation("add DlEquipment")
	 public DlResponse add(@RequestBody DlEquipment bean, HttpServletRequest request) {
		equipmentService.save(bean);
		return DlResponse.ok(bean);
    }

	@PutMapping(value = "/{id}")
	@OperationLogAnn("update DlEquipment")
	@ApiOperation("update DlEquipment By id")
	public DlResponse update(@PathVariable("id")int id,@RequestBody DlEquipment bean,HttpServletRequest request) {
		bean.setId(id);
		equipmentService.update(bean);
		return DlResponse.ok(bean);
    }
	
	@GetMapping(value = "/{id}")
	@ApiOperation("get DlEquipment By id")
	public DlResponse get(@PathVariable("id")int id) {
		DlEquipment equipment = equipmentService.getById(id);
		return DlResponse.ok(equipment);
	}
	
	@DeleteMapping(value = "/{id}")
	@OperationLogAnn("delete DlEquipment")
	@ApiOperation("delete DlEquipment By id")
	public DlResponse delete(@PathVariable("id")int id) {
		equipmentService.delete(id);
		return DlResponse.ok(null);
	}

//	private Specification<DlEquipment> buildSpecByQuery() {
//		HashMap<String,String> map = getRequestParamMap();
//
//		return (Specification<DlEquipment>) (root, query, criteriaBuilder) -> {
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
//	}

}
