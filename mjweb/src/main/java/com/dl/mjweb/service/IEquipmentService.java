package com.dl.mjweb.service;

import com.dl.common.model.entity.DlEquipment;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IEquipmentService {
    void delete(int id);

    @NonNull
    DlEquipment getById(int id);

    @NonNull
    Page page(Specification<DlEquipment> spec, Pageable pageable);

    void save(DlEquipment bean);

    void update(DlEquipment bean);

    List<DlEquipment> findAll();
}
