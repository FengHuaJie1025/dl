package com.dl.mjapp.service.imp;

import com.dl.common.model.entity.DLArea;
import com.dl.common.repository.AreaRepository;
import com.dl.mjapp.service.IAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AreaServiceImpl implements IAreaService {

    @Autowired
    private AreaRepository repository;

//    @Override
//    public List<DLArea> listByRegionId(Specification<DLArea> spec) {
//        List<DLArea> areas = areaRepository.findAll(spec);
//        return areas;
//    }

    @Override
    public List<DLArea> findAll(Specification<DLArea> spec) {
        List<DLArea> areas = repository.findAll(spec);
        return areas;
    }

    @Override
    public DLArea getById(int id) {
        DLArea area = repository.findById(id).get();
        return area;
    }
}
