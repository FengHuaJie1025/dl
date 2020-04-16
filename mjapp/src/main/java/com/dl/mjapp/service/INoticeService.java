package com.dl.mjapp.service;

import com.dl.common.model.entity.DlNotice;
import com.dl.common.model.entity.DlNotice;
import com.dl.common.model.entity.DlUserNotice;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;

public interface INoticeService {

    List<DlNotice> findAll(Specification<DlNotice> spec);

    DlNotice getById(int id);

    Set<DlNotice> getNoticeSet(int userid);

    Set<Integer> getReadNoticeidsSet(int userid);

    void readNotice(DlUserNotice userNotice);
}
