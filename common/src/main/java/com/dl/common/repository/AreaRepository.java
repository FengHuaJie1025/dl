package com.dl.common.repository;

import com.dl.common.model.entity.DLArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<DLArea, Integer>, JpaSpecificationExecutor<DLArea> {
}
