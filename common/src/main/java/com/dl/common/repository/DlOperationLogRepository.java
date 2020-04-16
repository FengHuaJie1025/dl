package com.dl.common.repository;

import com.dl.common.model.entity.DlOperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DlOperationLogRepository extends JpaRepository<DlOperationLog, Integer>, JpaSpecificationExecutor<DlOperationLog> {
}
