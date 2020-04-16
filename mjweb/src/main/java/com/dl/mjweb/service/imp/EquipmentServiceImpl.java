package com.dl.mjweb.service.imp;

import com.dl.common.model.entity.DlEquipment;
import com.dl.common.repository.EquipmentRepository;
import com.dl.mjweb.service.IEquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class EquipmentServiceImpl implements IEquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public void delete(int id) {
        if (!equipmentRepository.existsById(id)){
            log.debug("DlEquipment doesn`t exist this id: "+id);
        }
        equipmentRepository.deleteById(id);
    }

    @Override
    public DlEquipment getById(int id) {
        if (!equipmentRepository.existsById(id)){
            log.debug("DlEquipment doesn`t exist this id: "+id);
            return null;
        }
        return equipmentRepository.findById(id).get();
    }

    @Override
    public Page page(Specification<DlEquipment> spec, Pageable pageable) {
        Page<DlEquipment> page = equipmentRepository.findAll(spec,pageable);
        return page;
    }

    @Override
    public void save(DlEquipment bean) {
        equipmentRepository.save(bean);
    }

    @Override
    public void update(DlEquipment bean) {
        equipmentRepository.save(bean);
    }

    @Override
    public List<DlEquipment> findAll() {
        return equipmentRepository.findAll();
    }
}
