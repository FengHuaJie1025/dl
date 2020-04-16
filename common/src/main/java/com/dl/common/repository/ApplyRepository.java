package com.dl.common.repository;

import com.dl.common.model.entity.DLAdverLog;
import com.dl.common.model.entity.DlApplys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ApplyRepository extends JpaRepository<DlApplys, Integer>, JpaSpecificationExecutor<DlApplys> {
    List<DlApplys> findByUserIdAndState(Integer userid, DlApplys.ApplyState agree);

    List<DlApplys> findByUserIdAndStateAndRoomid(Integer userid, DlApplys.ApplyState agree, Integer roomid);

    List<DlApplys> findByRoomidInAndFamtypeNot(Set<Integer> roomids, DlApplys.Famtype owner);
}
