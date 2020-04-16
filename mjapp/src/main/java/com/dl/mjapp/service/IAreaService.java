package com.dl.mjapp.service;

import com.dl.common.model.entity.DLArea;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IAreaService {
//    List<DLArea> listByRegionId(Specification<DLArea> spec);

    List<DLArea> findAll(Specification<DLArea> spec);

    DLArea getById(int id);
}
