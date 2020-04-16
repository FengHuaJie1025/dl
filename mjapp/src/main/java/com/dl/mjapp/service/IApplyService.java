package com.dl.mjapp.service;

import com.dl.common.model.entity.DlApplys;

import java.util.List;
import java.util.Set;

public interface IApplyService {
    void save(DlApplys applys);

    List<DlApplys> findByUserId(Integer userid);

    List<DlApplys> findByUserIdAndState(Integer userid, DlApplys.ApplyState agree);

    List<DlApplys> findByUserIdAndStateAndRoomid(Integer userid, DlApplys.ApplyState agree, Integer roomid);

    void update(DlApplys dlApplys);

    List<DlApplys> findByRoomidInAndFamtypeNot(Set<Integer> roomids, DlApplys.Famtype owner);
}
