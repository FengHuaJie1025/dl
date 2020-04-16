package com.dl.common.repository;

import com.dl.common.model.entity.DlUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DlUserRepository extends JpaRepository<DlUser, Integer>, JpaSpecificationExecutor<DlUser> {
}
