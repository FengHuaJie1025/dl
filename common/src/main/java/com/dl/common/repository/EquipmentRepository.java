package com.dl.common.repository;

import com.dl.common.model.entity.DlEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EquipmentRepository extends JpaRepository<DlEquipment, Integer>, JpaSpecificationExecutor<DlEquipment> {
    List<DlEquipment> findByPid(String pid);

    List<DlEquipment> findByGateidIn(Set<Integer> gateids);
}
