package com.dl.common.repository;

import com.dl.common.model.entity.DlNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<DlNotice, Integer>, JpaSpecificationExecutor<DlNotice> {
}
