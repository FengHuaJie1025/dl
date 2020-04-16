package com.dl.common.repository;

import com.dl.common.model.entity.Account;
import com.dl.common.model.entity.DLAdver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdverRepository extends JpaRepository<DLAdver, Integer>, JpaSpecificationExecutor<DLAdver> {
    List<DLAdver> findByAreaid(Integer areaid);

}
