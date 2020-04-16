package com.dl.mjapp.service;

import com.dl.common.model.entity.DLAdver;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IAdverService {

    List<DLAdver> findAll(Specification<DLAdver> spec);

    DLAdver getById(int id);

    DLAdver getAdverByAreaId(Integer areaid);

}
