package com.dl.mjapp.service;

import com.dl.common.model.entity.DLRoom;
import com.dl.common.model.entity.DLRoom;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;

public interface IRoomService {

    List<DLRoom> findAll(Specification<DLRoom> spec);

    DLRoom getById(int id);

    List<DLRoom> findAllById(Set<Integer> roomids);
}
