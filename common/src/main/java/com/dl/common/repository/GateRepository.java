package com.dl.common.repository;

import com.dl.common.model.entity.DLGate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GateRepository extends JpaRepository<DLGate, Integer>, JpaSpecificationExecutor<DLGate> {
}
