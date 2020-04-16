package com.dl.mjweb.controller;

import com.dl.common.model.entity.Account;
import com.dl.common.model.entity.DLArea;
import com.dl.common.pojo.DlResponse;
import com.dl.common.utils.QueryUtil;
import com.dl.mjweb.aop.OperationLogAnn;
import com.dl.mjweb.service.IAreaService;
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
@RequestMapping(value = "area")
public class AreaController extends BaseController {

    @Autowired
    private final IAreaService areaService;

    @GetMapping(value = "/list")
    @ApiOperation("get All DLArea")
    public DlResponse list() {
        Specification<DLArea> spec = getDlAreaSpecification();
        List<DLArea> list = areaService.findAll(spec);
        return DlResponse.ok(list);
    }

    @GetMapping(value = "page")
    @ApiOperation("page DLArea")
    public DlResponse page(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Specification<DLArea> spec = getDlAreaSpecification();
        Page page = areaService.page(spec, pageable);
        return DlResponse.ok(page);
    }

    private Specification<DLArea> getDlAreaSpecification() {
        HashMap<String, String> map = this.getRequestParamMap();
        return QueryUtil.buildSpec(DLArea.class,map,"name");
    }

    @PostMapping
    @OperationLogAnn("add DLArea")
    @ApiOperation("add DLArea")
    public DlResponse add(@RequestBody DLArea bean, HttpServletRequest request) {
        areaService.save(bean);
        return DlResponse.ok(bean);
    }

    @PutMapping(value = "/{id}")
    @OperationLogAnn("update DLArea")
    @ApiOperation("update DLArea By id")
    public DlResponse update(@PathVariable("id") int id, @RequestBody DLArea bean, HttpServletRequest request) {
        bean.setId(id);
        areaService.update(bean);
        return DlResponse.ok(bean);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("get DLArea By id")
    public DlResponse get(@PathVariable("id") int id) {
        DLArea area = areaService.getById(id);
        return DlResponse.ok(area);
    }

    @DeleteMapping(value = "/{id}")
    @OperationLogAnn("delete DLArea")
    @ApiOperation("delete DLArea By id")
    public DlResponse delete(@PathVariable("id") int id) {
        areaService.delete(id);
        return DlResponse.ok(null);
    }

//	private Specification<DLArea> buildSpecByQuery() {
//		HashMap<String,String> map = getRequestParamMap();
//
//		return (Specification<DLArea>) (root, query, criteriaBuilder) -> {
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
//}

}
