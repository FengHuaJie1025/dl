package com.dl.mjweb.service;

import com.dl.common.model.entity.DlUserRoom;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IDlUserRoomService {
    void delete(int id);

    @NonNull
    DlUserRoom getById(int id);

    @NonNull
    Page page(Specification<DlUserRoom> spec, Pageable pageable);

    void save(DlUserRoom bean);

    void update(DlUserRoom bean);

    List<DlUserRoom> findAll();
}
