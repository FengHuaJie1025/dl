package com.dl.common.repository;

import com.dl.common.model.entity.DlOpenlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenlogRepository extends JpaRepository<DlOpenlog, Integer>, JpaSpecificationExecutor<DlOpenlog> {
}
