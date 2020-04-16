package com.dl.mjweb.controller;

import com.dl.common.model.entity.DLRoom;
import com.dl.common.pojo.DlResponse;
import com.dl.common.utils.QueryUtil;
import com.dl.mjweb.aop.OperationLogAnn;
import com.dl.mjweb.service.IRoomService;
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
@RequestMapping(value="room")
public class RoomController extends BaseController  {

	@Autowired
	private final IRoomService roomService;

	@GetMapping(value = "/list")
	@ApiOperation("get All DLRoom")
	public DlResponse list() {
		List<DLRoom> list = roomService.findAll();
		return DlResponse.ok(list);
	}
	
	@GetMapping(value="page")
	@ApiOperation("page DLRoom")
	public DlResponse page(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize) {
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		HashMap<String, String> map = this.getRequestParamMap();
		Specification<DLRoom> spec = QueryUtil.buildSpec(DLRoom.class,map,"roomName");
		Page page=  roomService.page(spec,pageable);
		return DlResponse.ok(page);
	}

	@PostMapping
	@OperationLogAnn("add DLRoom")
	@ApiOperation("add DLRoom")
	 public DlResponse add(@RequestBody DLRoom bean, HttpServletRequest request) {
		roomService.save(bean);
		return DlResponse.ok(bean);
    }

	@PutMapping(value = "/{id}")
	@OperationLogAnn("update DLRoom")
	@ApiOperation("update DLRoom By id")
	public DlResponse update(@PathVariable("id")int id,@RequestBody DLRoom bean,HttpServletRequest request) {
		bean.setId(id);
		roomService.update(bean);
		return DlResponse.ok(bean);
    }
	
	@GetMapping(value = "/{id}")
	@ApiOperation("get DLRoom By id")
	public DlResponse get(@PathVariable("id")int id) {
		DLRoom room = roomService.getById(id);
		return DlResponse.ok(room);
	}
	
	@DeleteMapping(value = "/{id}")
	@OperationLogAnn("delete DLRoom")
	@ApiOperation("delete DLRoom By id")
	public DlResponse delete(@PathVariable("id")int id) {
		roomService.delete(id);
		return DlResponse.ok(null);
	}

//	private Specification<DLRoom> buildSpecByQuery() {
//		HashMap<String,String> map = getRequestParamMap();
//
//		return (Specification<DLRoom>) (root, query, criteriaBuilder) -> {
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
