package com.dl.mjapp.service;

import com.dl.common.model.entity.DLGate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IGateService {

    List<DLGate> findAll(Specification<DLGate> spec);

    DLGate getById(int id);
}
