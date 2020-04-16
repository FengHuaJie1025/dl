package com.dl.mjapp.service.imp;

import com.dl.common.model.entity.DLAdverLog;
import com.dl.common.model.entity.DlApplys;
import com.dl.common.repository.AdverLogRepository;
import com.dl.common.repository.ApplyRepository;
import com.dl.common.utils.QueryUtil;
import com.dl.common.utils.SearchFilter;
import com.dl.mjapp.service.IAdverLogService;
import com.dl.mjapp.service.IApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ApplyServiceImpl implements IApplyService {

    @Autowired
    private ApplyRepository repository;

    @Override
    public void save(DlApplys applys) {
        repository.save(applys);
    }

    @Override
    public List<DlApplys> findByUserId(Integer userid) {
        Collection<SearchFilter> filters = new ArrayList<>();
        filters.add(new SearchFilter("userId",SearchFilter.Operator.EQ,userid));
        List<DlApplys> list = repository.findAll(QueryUtil.bySearchFilter(filters));
        return list;
    }

    @Override
    public List<DlApplys> findByUserIdAndState(Integer userid, DlApplys.ApplyState agree) {
        List<DlApplys> list = repository.findByUserIdAndState(userid,agree);
        return list;
    }

    @Override
    public List<DlApplys> findByUserIdAndStateAndRoomid(Integer userid, DlApplys.ApplyState agree, Integer roomid) {
        List<DlApplys> list = repository.findByUserIdAndStateAndRoomid(userid,agree,roomid);
        return list;
    }

    @Override
    public void update(DlApplys dlApplys) {
        repository.save(dlApplys);
    }

    @Override
    public List<DlApplys> findByRoomidInAndFamtypeNot(Set<Integer> roomids, DlApplys.Famtype owner) {
        List<DlApplys> list = repository.findByRoomidInAndFamtypeNot(roomids,owner);
        return list;
    }
}
