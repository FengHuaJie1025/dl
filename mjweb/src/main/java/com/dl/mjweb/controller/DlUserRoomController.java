package com.dl.mjweb.controller;

import com.dl.common.model.entity.DlUserRoom;
import com.dl.common.pojo.DlResponse;
import com.dl.common.utils.QueryUtil;
import com.dl.mjweb.aop.OperationLogAnn;
import com.dl.mjweb.service.IDlUserRoomService;
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
@RequestMapping(value="userroom")
public class DlUserRoomController extends BaseController  {

	@Autowired
	private final IDlUserRoomService userroomService;

	@GetMapping(value = "/list")
	@ApiOperation("get All DlUserRoom")
	public DlResponse list() {
		List<DlUserRoom> list = userroomService.findAll();
		return DlResponse.ok(list);
	}
	
	@GetMapping(value="page")
	@ApiOperation("page DlUserRoom")
	public DlResponse page(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize) {
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		HashMap<String, String> map = this.getRequestParamMap();
		Specification<DlUserRoom> spec = QueryUtil.buildSpec(DlUserRoom.class,map);
		Page page=  userroomService.page(spec,pageable);
		return DlResponse.ok(page);
	}

	@PostMapping
	@OperationLogAnn("add DlUserRoom")
	@ApiOperation("add DlUserRoom")
	 public DlResponse add(@RequestBody DlUserRoom bean, HttpServletRequest request) {
		userroomService.save(bean);
		return DlResponse.ok(bean);
    }

	@PutMapping(value = "/{id}")
	@OperationLogAnn("update DlUserRoom")
	@ApiOperation("update DlUserRoom By id")
	public DlResponse update(@PathVariable("id")int id,@RequestBody DlUserRoom bean,HttpServletRequest request) {
		bean.setId(id);
		userroomService.update(bean);
		return DlResponse.ok(bean);
    }
	
	@GetMapping(value = "/{id}")
	@ApiOperation("get DlUserRoom By id")
	public DlResponse get(@PathVariable("id")int id) {
		DlUserRoom userroom = userroomService.getById(id);
		return DlResponse.ok(userroom);
	}
	
	@DeleteMapping(value = "/{id}")
	@OperationLogAnn("delete DlUserRoom")
	@ApiOperation("delete DlUserRoom By id")
	public DlResponse delete(@PathVariable("id")int id) {
		userroomService.delete(id);
		return DlResponse.ok(null);
	}

//	private Specification<DlUserRoom> buildSpecByQuery() {
//		HashMap<String,String> map = getRequestParamMap();
//
//		return (Specification<DlUserRoom>) (root, query, criteriaBuilder) -> {
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
