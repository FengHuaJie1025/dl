package com.dl.mjweb.service.imp;

import com.dl.common.model.entity.DLGate;
import com.dl.common.repository.GateRepository;
import com.dl.mjweb.service.IGateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class GateServiceImpl implements IGateService {

    @Autowired
    private GateRepository gateRepository;

    @Override
    public void delete(int id) {
        if (!gateRepository.existsById(id)){
            log.debug("DLGate doesn`t exist this id: "+id);
        }
        gateRepository.deleteById(id);
    }

    @Override
    public DLGate getById(int id) {
        if (!gateRepository.existsById(id)){
            log.debug("DLGate doesn`t exist this id: "+id);
            return null;
        }
        return gateRepository.findById(id).get();
    }

    @Override
    public Page page(Specification<DLGate> spec, Pageable pageable) {
        Page<DLGate> page = gateRepository.findAll(spec,pageable);
        return page;
    }

    @Override
    public void save(DLGate bean) {
        gateRepository.save(bean);
    }

    @Override
    public void update(DLGate bean) {
        gateRepository.save(bean);
    }

    @Override
    public List<DLGate> findAll() {
        return gateRepository.findAll();
    }
}
