package com.dl.mjweb.service;

import com.dl.common.model.entity.DLRoom;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IRoomService {
    void delete(int id);

    @NonNull
    DLRoom getById(int id);

    @NonNull
    Page page(Specification<DLRoom> spec, Pageable pageable);

    void save(DLRoom bean);

    void update(DLRoom bean);

    List<DLRoom> findAll();
}
