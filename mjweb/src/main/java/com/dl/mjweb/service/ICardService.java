package com.dl.mjweb.service;

import com.dl.common.model.entity.DlUserCard;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ICardService {
    void delete(int id);

    @NonNull
    DlUserCard getById(int id);

    @NonNull
    Page page(Specification<DlUserCard> spec, Pageable pageable);

    void save(DlUserCard bean);

    void update(DlUserCard bean);

    List<DlUserCard> findAll();
}
