package com.dl.common.repository;

import com.dl.common.model.entity.DLRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<DLRoom, Integer>, JpaSpecificationExecutor<DLRoom> {

}
