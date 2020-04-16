package com.dl.common.repository;

import com.dl.common.model.entity.DLAdver;
import com.dl.common.model.entity.DLAdverLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdverLogRepository extends JpaRepository<DLAdverLog, Integer>, JpaSpecificationExecutor<DLAdverLog> {
}
