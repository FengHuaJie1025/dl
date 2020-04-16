package com.dl.mjweb.service;

import com.dl.common.model.entity.Permission;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IPermissionService {
    void delete(int id);

    @NonNull
    Permission getById(int id);

    @NonNull
    Page page(Specification<Permission> spec, Pageable pageable);

    void save(Permission bean);

    void update(Permission bean);

    List<Permission> findAll();
}
