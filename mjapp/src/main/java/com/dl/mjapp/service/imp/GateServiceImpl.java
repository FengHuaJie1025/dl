package com.dl.mjapp.service.imp;

import com.dl.common.model.entity.DLGate;
import com.dl.common.repository.AreaRepository;
import com.dl.common.repository.GateRepository;
import com.dl.mjapp.service.IAreaService;
import com.dl.mjapp.service.IGateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GateServiceImpl implements IGateService {

    @Autowired
    private GateRepository repository;

    @Override
    public List<DLGate> findAll(Specification<DLGate> spec) {
        List<DLGate> gates = repository.findAll(spec);
        return gates;
    }

    @Override
    public DLGate getById(int id) {
        DLGate gate = repository.findById(id).get();
        return gate;
    }
}
