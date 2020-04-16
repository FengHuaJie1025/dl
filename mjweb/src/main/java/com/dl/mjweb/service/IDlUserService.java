package com.dl.mjweb.service;

import com.dl.common.model.entity.DlUser;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IDlUserService {
    void delete(int id);

    @NonNull
    DlUser getById(int id);

    @NonNull
    Page page(Specification<DlUser> spec, Pageable pageable);

    void save(DlUser bean);

    void update(DlUser bean);

    List<DlUser> findAll();
}
