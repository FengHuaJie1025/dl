package com.dl.common.repository;

import com.dl.common.model.entity.DLArea;
import com.dl.common.model.entity.DlSmscode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmscodeRepository extends JpaRepository<DlSmscode, Integer>, JpaSpecificationExecutor<DlSmscode> {
}
