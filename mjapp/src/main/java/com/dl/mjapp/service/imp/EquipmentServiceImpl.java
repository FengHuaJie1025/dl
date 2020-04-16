package com.dl.mjapp.service.imp;

import com.dh.buildlock.pub.DHDataProcessPub;
import com.dl.common.model.entity.DLAdver;
import com.dl.common.model.entity.DLArea;
import com.dl.common.model.entity.DlEquipment;
import com.dl.common.repository.AdverRepository;
import com.dl.common.repository.EquipmentRepository;
import com.dl.mjapp.service.IAdverService;
import com.dl.mjapp.service.IEquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class EquipmentServiceImpl implements IEquipmentService {

    @Autowired
    private EquipmentRepository repository;

    @Override
    public DlEquipment getEquipment(String pid) {
        //pid做唯一约束！
        List<DlEquipment> list = repository.findByPid(pid);
        if (list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public DLArea getAreaByPid(String pid) {
        return null;
    }

    @Override
    public List<DlEquipment> findByGateidIn(Set<Integer> gateids) {
        List<DlEquipment> list = repository.findByGateidIn(gateids);
        return list;
    }

    @Override
    public List<DlEquipment> generateHexkeys(List<DlEquipment> equipments) {
        DHDataProcessPub process = DHDataProcessPub.lockInit();
        equipments.forEach(equipment -> {
            String hexKey = process.buildServiceOperateWXAccess(equipment.getPid(), Long.toHexString(Long.parseLong(equipment.getLockId())), 1, 1);
            equipment.setHexkey(hexKey);
        });
        return equipments;
    }
}
