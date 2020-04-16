package com.dl.mjapp.service.imp;

import com.dl.common.model.entity.DlNotice;
import com.dl.common.model.entity.DlUserNotice;
import com.dl.common.repository.NoticeRepository;
import com.dl.mjapp.service.IDlUserService;
import com.dl.mjapp.service.INoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class NoticeServiceImpl implements INoticeService {

    @Autowired
    private NoticeRepository repository;

    /**
     *  需要使用userService提供的根据userid查询areaid的服务，就无需重复实现
     */
    @Autowired
    private IDlUserService userService;

    @Override
    public List<DlNotice> findAll(Specification<DlNotice> spec) {
        List<DlNotice> notices = repository.findAll(spec);
        return notices;
    }

    @Override
    public DlNotice getById(int id) {
        DlNotice notice = repository.findById(id).get();
        return notice;
    }

    @Override
    public Set<DlNotice> getNoticeSet(int userid) {
        Integer[] areaids = userService.getAreaidByUserid(userid);

        return null;
    }

    @Override
    public Set<Integer> getReadNoticeidsSet(int userid) {
        return null;
    }

    @Override
    public void readNotice(DlUserNotice userNotice) {

    }
}
