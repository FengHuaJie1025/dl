package com.dl.common.repository;

import com.dl.common.model.entity.DlProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<DlProject, Integer>, JpaSpecificationExecutor<DlProject> {
}
