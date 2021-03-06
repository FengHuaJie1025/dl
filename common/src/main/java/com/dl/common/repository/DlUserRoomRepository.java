package com.dl.common.repository;

import com.dl.common.model.entity.DlUserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DlUserRoomRepository extends JpaRepository<DlUserRoom, Integer>, JpaSpecificationExecutor<DlUserRoom> {
}
