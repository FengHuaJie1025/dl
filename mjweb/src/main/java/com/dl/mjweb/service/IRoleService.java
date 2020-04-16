package com.dl.mjweb.service;

import com.dl.common.model.entity.Role;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IRoleService {
    void delete(int id);

    @NonNull
    Role getById(int id);

    @NonNull
    Page page(Specification<Role> spec, Pageable pageable);

    void save(Role bean);

    void update(Role bean);

    List<Role> findAll();
}
