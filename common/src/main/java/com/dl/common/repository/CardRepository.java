package com.dl.common.repository;

import com.dl.common.model.entity.DlUserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<DlUserCard, Integer>, JpaSpecificationExecutor<DlUserCard> {
}
