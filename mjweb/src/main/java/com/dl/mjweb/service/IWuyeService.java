package com.dl.mjweb.service;

import com.dl.common.model.entity.Dlwuye;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IWuyeService {
    void delete(int id);

    @NonNull
    Dlwuye getById(int id);

    @NonNull
    Page page(Specification<Dlwuye> spec, Pageable pageable);

    void save(Dlwuye bean);

    void update(Dlwuye bean);

    List<Dlwuye> findAll();
}
