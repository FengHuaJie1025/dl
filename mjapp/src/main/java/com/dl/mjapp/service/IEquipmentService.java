package com.dl.mjapp.service;

import com.dl.common.model.entity.DLArea;
import com.dl.common.model.entity.DlEquipment;

import java.util.List;
import java.util.Set;

public interface IEquipmentService {
    DlEquipment getEquipment(String pid);

    DLArea getAreaByPid(String pid);

    List<DlEquipment> findByGateidIn(Set<Integer> gateids);

    List<DlEquipment> generateHexkeys(List<DlEquipment> equipments);
}
