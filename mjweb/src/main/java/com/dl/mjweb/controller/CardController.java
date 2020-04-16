package com.dl.mjweb.controller;

import com.dl.common.model.entity.DlUserCard;
import com.dl.common.pojo.DlResponse;
import com.dl.common.utils.QueryUtil;
import com.dl.mjweb.aop.OperationLogAnn;
import com.dl.mjweb.service.ICardService;
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
@RequestMapping(value="card")
public class CardController extends BaseController  {

	@Autowired
	private final ICardService cardService;

	@GetMapping(value = "/list")
	@ApiOperation("get All DlUserCard")
	public DlResponse list() {
		List<DlUserCard> list = cardService.findAll();
		return DlResponse.ok(list);
	}
	
	@GetMapping(value="page")
	@ApiOperation("page DlUserCard")
	public DlResponse page(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize) {
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		HashMap<String, String> map = this.getRequestParamMap();
		Specification<DlUserCard> spec = QueryUtil.buildSpec(DlUserCard.class,map,"cardid","userid");
		Page page=  cardService.page(spec,pageable);
		return DlResponse.ok(page);
	}

	@PostMapping
	@OperationLogAnn("add DlUserCard")
	@ApiOperation("add DlUserCard")
	 public DlResponse add(@RequestBody DlUserCard bean, HttpServletRequest request) {
		cardService.save(bean);
		return DlResponse.ok(bean);
    }

	@PutMapping(value = "/{id}")
	@OperationLogAnn("update DlUserCard")
	@ApiOperation("update DlUserCard By id")
	public DlResponse update(@PathVariable("id")int id,@RequestBody DlUserCard bean,HttpServletRequest request) {
		bean.setId(id);
		cardService.update(bean);
		return DlResponse.ok(bean);
    }
	
	@GetMapping(value = "/{id}")
	@ApiOperation("get DlUserCard By id")
	public DlResponse get(@PathVariable("id")int id) {
		DlUserCard card = cardService.getById(id);
		return DlResponse.ok(card);
	}
	
	@DeleteMapping(value = "/{id}")
	@OperationLogAnn("delete DlUserCard")
	@ApiOperation("delete DlUserCard By id")
	public DlResponse delete(@PathVariable("id")int id) {
		cardService.delete(id);
		return DlResponse.ok(null);
	}

//	private Specification<DlUserCard> buildSpecByQuery() {
//		HashMap<String,String> map = getRequestParamMap();
//
//		return (Specification<DlUserCard>) (root, query, criteriaBuilder) -> {
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
