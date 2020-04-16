package com.dl.mjapp.service.imp;

import com.dl.common.model.entity.*;
import com.dl.common.repository.*;
import com.dl.common.utils.QueryUtil;
import com.dl.common.utils.SearchFilter;
import com.dl.mjapp.service.IDlUserService;
import com.dl.mjapp.service.ISmscodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
public class DlUserServiceImpl implements IDlUserService {

    @Autowired
    private DlUserRepository userRepository;

    @Autowired
    private DlUserRoomRepository userRoomRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ISmscodeService smscodeService;

    @Override
    public void delete(int id) {
        if (!userRepository.existsById(id)){
            log.debug("DlUser doesn`t exist this id: "+id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public DlUser getById(int id) {
        if (!userRepository.existsById(id)){
            log.debug("DlUser doesn`t exist this id: "+id);
            return null;
        }
        return userRepository.findById(id).get();
    }

    @Override
    public Page page(Specification<DlUser> spec, Pageable pageable) {
        Page<DlUser> page = userRepository.findAll(spec,pageable);
        return page;
    }

    @Override
    public void save(DlUser bean) {
        userRepository.save(bean);
    }

    @Override
    public void update(DlUser bean) {
        userRepository.save(bean);
    }

    @Override
    public List<DlUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Integer[] getAreaidByUserid(int userid) {
        List<DlUserRoom> userRooms = userRoomRepository.findAll();
        if (userRooms.isEmpty()){
            return new Integer[0];
        }
        List<DLRoom> rooms = roomRepository.findAll();


        return null;
    }

    @Override
    public DlUser checkAccount(DlUser dlUser,String code) {
        smscodeService.checkSmsCode(dlUser.getPhone(),code);
        Collection<SearchFilter> filters = new ArrayList<>();
        filters.add(new SearchFilter("phone",SearchFilter.Operator.EQ,dlUser.getPhone()));
        Optional<DlUser> userOptional = userRepository.findOne(QueryUtil.bySearchFilter(filters));
        if (!userOptional.isPresent()){
            dlUser = userRepository.save(dlUser);
        }
        Optional<DlUser> userOptional2 = userRepository.findById(dlUser.getId());
        return userOptional2.get();
    }
}
