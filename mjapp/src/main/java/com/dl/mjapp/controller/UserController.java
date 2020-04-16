package com.dl.mjapp.controller;

import com.dl.common.model.entity.*;
import com.dl.common.pojo.DlResponse;
import com.dl.mjapp.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="user")
public class UserController extends BaseController  {

	@Autowired
	private final IDlUserService userService;

	@Autowired
	private final IApplyService applyService;

	@Autowired
	private final IRoomService roomService;

	@Autowired
	private final IEquipmentService equipmentService;

	@GetMapping(value = "/update")
	@ApiOperation("update userInfo")
	public DlResponse updateUserInfo(DlUser user) {
		userService.update(user);
		return DlResponse.ok(user);
	}

	@PostMapping(value = "applyRoom")
	@ApiOperation("applyRoom")
	public DlResponse applyRoom(DlApplys applys) throws Exception {
		applyService.save(applys);
		return DlResponse.ok();
	}

	@PostMapping(value = "applyRecord")
	@ApiOperation("applyRecord")
	public DlResponse applyRecord(@RequestParam("userid")Integer userid) throws Exception {
		List<DlApplys> list = applyService.findByUserId(userid);
		return DlResponse.ok(list);
	}

	@PostMapping(value = "myRoom")
	@ApiOperation("myRoom")
	public DlResponse myRoom(@RequestParam("userid")Integer userid) throws Exception {
		List<DlApplys> list = applyService.findByUserIdAndState(userid,DlApplys.ApplyState.AGREE);
		if (list.isEmpty()){
			return DlResponse.ok();
		}
		Set<Integer> roomids = list.stream().map(applys -> applys.getRoomid()).collect(Collectors.toSet());
		List<DLRoom> rooms =roomService.findAllById(roomids);
		return DlResponse.ok(rooms);
	}

	@PostMapping(value = "authApplys")
	@ApiOperation("authApplys")
	public DlResponse authApplys(@RequestParam("userid")Integer userid,DlApplys dlApplys) throws Exception {
		List<DlApplys> list = applyService.findByUserIdAndStateAndRoomid(userid,DlApplys.ApplyState.AGREE,dlApplys.getRoomid());
		if (list.isEmpty()){
			throw new RuntimeException("该用户不是该房间业主，无法授权住户");
		}
		dlApplys.setState(DlApplys.ApplyState.AGREE);
		applyService.update(dlApplys);
		return DlResponse.ok();
	}

	@PostMapping(value = "myKeys")
	@ApiOperation("myKeys")
	public DlResponse myKeys(@RequestParam("userid")Integer userid) throws Exception {
		List<DlApplys> list = applyService.findByUserIdAndState(userid,DlApplys.ApplyState.AGREE);
		if (list.isEmpty()){
			return DlResponse.ok();
		}
		//事实上，空集合调用stream也不会报错
		Set<Integer> roomids = list.stream().map(applys -> applys.getRoomid()).collect(Collectors.toSet());
		List<DLRoom> rooms =roomService.findAllById(roomids);
		Set<Integer> gateids = rooms.stream().map(room -> room.getGateid()).collect(Collectors.toSet());
		List<DlEquipment> equipments = equipmentService.findByGateidIn(gateids);
		equipments = equipmentService.generateHexkeys(equipments);
		return DlResponse.ok(equipments);
	}

	@PostMapping(value = "login")
	@ApiOperation("login")
	public DlResponse login(DlUser dlUser,@RequestParam("code") String code) throws Exception {
		DlUser account = userService.checkAccount(dlUser,code);

		return DlResponse.ok(account);
	}

	@PostMapping(value = "residentManage")
	@ApiOperation("residentManage")
	public DlResponse residentManage(@RequestParam("userid")Integer userid) throws Exception {
		List<DlApplys> list = applyService.findByUserIdAndState(userid,DlApplys.ApplyState.AGREE);
		if (list.isEmpty()){
			return DlResponse.ok();
		}
		Set<Integer> roomids = list.stream().map(applys -> applys.getRoomid()).collect(Collectors.toSet());
		List<DlApplys> applys = applyService.findByRoomidInAndFamtypeNot(roomids,DlApplys.Famtype.OWNER);
		return DlResponse.ok(applys);
	}

	@PostMapping(value = "addResident")
	@ApiOperation("addResident")
	public DlResponse addResident(@RequestParam("userid")Integer userid,DlApplys dlApplys) throws Exception {
		@NonNull DlUser user = userService.getById(userid);
		List<DlApplys> list = applyService.findByUserIdAndState(userid,DlApplys.ApplyState.AGREE);
		if (list.isEmpty()){
			throw new RuntimeException("该用户不是该房间业主，无法授权住户");
		}
		Set<Integer> roomids = list.stream().map(applys -> applys.getRoomid()).collect(Collectors.toSet());
		if (!roomids.contains(dlApplys.getRoomid())){
			throw new RuntimeException("该用户不是该房间业主，无法授权住户");
		}
		dlApplys.setState(DlApplys.ApplyState.AGREE);
		dlApplys.setAgreeman(user.getName());
		return DlResponse.ok(dlApplys);
	}

}
