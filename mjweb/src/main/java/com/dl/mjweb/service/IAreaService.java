package com.dl.mjweb.service;

import com.dl.common.model.entity.Account;
import com.dl.common.model.entity.DLArea;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IAreaService {
    void delete(int id);

    @NonNull
    DLArea getById(int id);

    @NonNull
    Page page(Specification<DLArea> spec, Pageable pageable);

    void save(DLArea bean);

    void update(DLArea bean);

    List<DLArea> findAll(Specification<DLArea> spec);

}
