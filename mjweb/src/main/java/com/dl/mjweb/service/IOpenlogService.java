package com.dl.mjweb.service;

import com.dl.common.model.entity.DlOpenlog;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IOpenlogService {
    void delete(int id);

    @NonNull
    DlOpenlog getById(int id);

    @NonNull
    Page page(Specification<DlOpenlog> spec, Pageable pageable);

    void save(DlOpenlog bean);

    void update(DlOpenlog bean);

    List<DlOpenlog> findAll();
}
