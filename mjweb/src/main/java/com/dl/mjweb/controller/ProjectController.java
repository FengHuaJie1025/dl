package com.dl.mjweb.controller;

import com.dl.common.model.entity.DlProject;
import com.dl.common.pojo.DlResponse;
import com.dl.common.utils.QueryUtil;
import com.dl.mjweb.aop.OperationLogAnn;
import com.dl.mjweb.service.IProjectService;
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
@RequestMapping(value="project")
public class ProjectController extends BaseController  {

	@Autowired
	private final IProjectService projectService;

	@GetMapping(value = "/list")
	@ApiOperation("get All DlProject")
	public DlResponse list() {
		List<DlProject> list = projectService.findAll();
		return DlResponse.ok(list);
	}
	
	@GetMapping(value="page")
	@ApiOperation("page DlProject")
	public DlResponse page(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize) {
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		HashMap<String, String> map = this.getRequestParamMap();
		Specification<DlProject> spec = QueryUtil.buildSpec(DlProject.class,map);
		Page page=  projectService.page(spec,pageable);
		return DlResponse.ok(page);
	}

	@PostMapping
	@OperationLogAnn("add DlProject")
	@ApiOperation("add DlProject")
	 public DlResponse add(@RequestBody DlProject bean, HttpServletRequest request) {
		projectService.save(bean);
		return DlResponse.ok(bean);
    }

	@PutMapping(value = "/{id}")
	@OperationLogAnn("update DlProject")
	@ApiOperation("update DlProject By id")
	public DlResponse update(@PathVariable("id")int id,@RequestBody DlProject bean,HttpServletRequest request) {
		bean.setId(id);
		projectService.update(bean);
		return DlResponse.ok(bean);
    }
	
	@GetMapping(value = "/{id}")
	@ApiOperation("get DlProject By id")
	public DlResponse get(@PathVariable("id")int id) {
		DlProject DlProject = projectService.getById(id);
		return DlResponse.ok(DlProject);
	}
	
	@DeleteMapping(value = "/{id}")
	@OperationLogAnn("delete DlProject")
	@ApiOperation("delete DlProject By id")
	public DlResponse delete(@PathVariable("id")int id) {
		projectService.delete(id);
		return DlResponse.ok(null);
	}

//	private Specification<DlProject> buildSpecByQuery() {
//		HashMap<String,String> map = getRequestParamMap();
//
//		return (Specification<DlProject>) (root, query, criteriaBuilder) -> {
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
