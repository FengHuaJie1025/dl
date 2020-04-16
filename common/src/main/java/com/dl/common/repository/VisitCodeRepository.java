package com.dl.common.repository;

import com.dl.common.model.entity.Account;
import com.dl.common.model.entity.DlVisiteCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitCodeRepository extends JpaRepository<DlVisiteCode, Integer>, JpaSpecificationExecutor<DlVisiteCode> {
}
