package com.dl.mjweb.controller;

import com.dl.common.model.entity.Account;
import com.dl.common.model.entity.DLArea;
import com.dl.common.pojo.DlResponse;
import com.dl.common.utils.QueryUtil;
import com.dl.mjweb.aop.OperationLogAnn;
import com.dl.mjweb.service.IAccountService;
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
@RequestMapping(value="account")
public class AccountController extends BaseController  {

	@Autowired
	private final IAccountService accountService;

	@PostMapping("login")
	@OperationLogAnn("login")
	@ApiOperation("login")
	public DlResponse login(@RequestBody Account bean, HttpServletRequest request) {
		Account account = accountService.checkAccount(bean);

		return DlResponse.ok(account);
	}

	@GetMapping(value = "/list")
	@ApiOperation("get All Account")
	public DlResponse list() {
		Specification<Account> spec = getAccountSpecification();
		List<Account> list = accountService.findAll(spec);
		return DlResponse.ok(list);
	}

	@GetMapping(value="page")
	@ApiOperation("page Account")
	public DlResponse page(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "20") int pageSize) {
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		Specification<Account> spec = getAccountSpecification();
		Page page=  accountService.page(spec,pageable);
		return DlResponse.ok(page);
	}

	private Specification<Account> getAccountSpecification() {
		HashMap<String, String> map = this.getRequestParamMap();
		return QueryUtil.buildSpec(Account.class,map,"name","phone");
	}

	@PostMapping
	@OperationLogAnn("add Account")
	@ApiOperation("add Account")
	 public DlResponse add(@RequestBody Account bean, HttpServletRequest request) {
		accountService.save(bean);
		return DlResponse.ok(bean);
    }

	@PutMapping(value = "/{id}")
	@OperationLogAnn("update Account")
	@ApiOperation("update Account By id")
	public DlResponse update(@PathVariable("id")int id,@RequestBody Account bean,HttpServletRequest request) {
		bean.setId(id);
		accountService.update(bean);
		return DlResponse.ok(bean);
    }
	
	@GetMapping(value = "/{id}")
	@ApiOperation("get Account By id")
	public DlResponse get(@PathVariable("id")int id) {
		Account account = accountService.getById(id);
		return DlResponse.ok(account);
	}
	
	@DeleteMapping(value = "/{id}")
	@OperationLogAnn("delete Account")
	@ApiOperation("delete Account By id")
	public DlResponse delete(@PathVariable("id")int id) {
		accountService.delete(id);
		return DlResponse.ok(null);
	}

//	private Specification<Account> buildSpecByQuery() {
//		HashMap<String,String> map = getRequestParamMap();
//
//		return (Specification<Account>) (root, query, criteriaBuilder) -> {
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
