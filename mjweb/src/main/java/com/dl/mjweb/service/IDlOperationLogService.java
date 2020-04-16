package com.dl.mjweb.service;

import com.dl.common.model.entity.DlOperationLog;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface IDlOperationLogService {
    void delete(int id);

    @NonNull
    DlOperationLog getById(int id);

    @NonNull
    Page page(Specification<DlOperationLog> spec, Pageable pageable);

    void save(DlOperationLog bean);

    void update(DlOperationLog bean);
}
