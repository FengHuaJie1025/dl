package com.dl.mjweb.service;

import com.dl.common.model.entity.DlProject;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IProjectService {
    void delete(int id);

    @NonNull
    DlProject getById(int id);

    @NonNull
    Page page(Specification<DlProject> spec, Pageable pageable);

    void save(DlProject bean);

    void update(DlProject bean);

    List<DlProject> findAll();
}
