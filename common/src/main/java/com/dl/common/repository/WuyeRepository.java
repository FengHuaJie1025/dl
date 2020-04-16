package com.dl.common.repository;

import com.dl.common.model.entity.Dlwuye;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WuyeRepository extends JpaRepository<Dlwuye, Integer>, JpaSpecificationExecutor<Dlwuye> {
}
