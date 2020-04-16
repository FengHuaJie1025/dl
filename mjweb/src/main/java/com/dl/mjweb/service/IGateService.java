package com.dl.mjweb.service;

import com.dl.common.model.entity.DLGate;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IGateService {
    void delete(int id);

    @NonNull
    DLGate getById(int id);

    @NonNull
    Page page(Specification<DLGate> spec, Pageable pageable);

    void save(DLGate bean);

    void update(DLGate bean);

    List<DLGate> findAll();
}
