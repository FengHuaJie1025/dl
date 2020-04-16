package com.dl.mjapp.controller;

import com.dl.common.model.entity.DlNotice;
import com.dl.common.model.entity.DlNotice;
import com.dl.common.model.entity.DlUserNotice;
import com.dl.common.pojo.DlResponse;
import com.dl.common.utils.QueryUtil;
import com.dl.mjapp.service.IDlUserService;
import com.dl.mjapp.service.INoticeService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="notice")
public class NoticeController extends BaseController  {

	@Autowired
	private final INoticeService noticeService;

	@Autowired
	private final IDlUserService userService;

	@GetMapping(value = "/getNotices")
	@ApiOperation("getNotices")
	public DlResponse getNotice(@RequestParam("userid")int userid) {
		//获取所有的通知列表(按时间排序，最近的排前面)
		Set<DlNotice> noticeSet = noticeService.getNoticeSet(userid);
		//获取到已读的通知id列表
		Set<Integer> readNoticeidsSet = noticeService.getReadNoticeidsSet(userid);
		for (DlNotice dlNotice : noticeSet) {
			if (readNoticeidsSet.contains(dlNotice.getId())){
				dlNotice.setRead(true);
			}
		}
		return DlResponse.ok(noticeSet);
	}


	@GetMapping(value = "readNotice")
	@ApiOperation("readNotice")
	public DlResponse read(DlUserNotice userNotice) throws Exception {
		noticeService.readNotice(userNotice);
		return DlResponse.ok();
	}

}
