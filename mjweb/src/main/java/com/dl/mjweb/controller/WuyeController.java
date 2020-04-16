package com.dl.mjweb.controller;

import com.dl.common.model.entity.Dlwuye;
import com.dl.common.pojo.DlResponse;
import com.dl.common.utils.QueryUtil;
import com.dl.mjweb.aop.OperationLogAnn;
import com.dl.mjweb.service.IWuyeService;
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
@RequestMapping(value="wuye")
public class WuyeController extends BaseController  {

	@Autowired
	private final IWuyeService wuyeService;

	@GetMapping(value = "/list")
	@ApiOperation("get All Dlwuye")
	public DlResponse list() {
		List<Dlwuye> list = wuyeService.findAll();
		return DlResponse.ok(list);
	}
	
	@GetMapping(value="page")
	@ApiOperation("page Dlwuye")
	public DlResponse page(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize) {
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		HashMap<String, String> map = this.getRequestParamMap();
		Specification<Dlwuye> spec = QueryUtil.buildSpec(Dlwuye.class,map);
		Page page=  wuyeService.page(spec,pageable);
		return DlResponse.ok(page);
	}

	@PostMapping
	@OperationLogAnn("add Dlwuye")
	@ApiOperation("add Dlwuye")
	 public DlResponse add(@RequestBody Dlwuye bean, HttpServletRequest request) {
		wuyeService.save(bean);
		return DlResponse.ok(bean);
    }

	@PutMapping(value = "/{id}")
	@OperationLogAnn("update Dlwuye")
	@ApiOperation("update Dlwuye By id")
	public DlResponse update(@PathVariable("id")int id,@RequestBody Dlwuye bean,HttpServletRequest request) {
		bean.setId(id);
		wuyeService.update(bean);
		return DlResponse.ok(bean);
    }
	
	@GetMapping(value = "/{id}")
	@ApiOperation("get Dlwuye By id")
	public DlResponse get(@PathVariable("id")int id) {
		Dlwuye wuye = wuyeService.getById(id);
		return DlResponse.ok(wuye);
	}
	
	@DeleteMapping(value = "/{id}")
	@OperationLogAnn("delete Dlwuye")
	@ApiOperation("delete Dlwuye By id")
	public DlResponse delete(@PathVariable("id")int id) {
		wuyeService.delete(id);
		return DlResponse.ok(null);
	}

//	private Specification<Dlwuye> buildSpecByQuery() {
//		HashMap<String,String> map = getRequestParamMap();
//
//		return (Specification<Dlwuye>) (root, query, criteriaBuilder) -> {
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
