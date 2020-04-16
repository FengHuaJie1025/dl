package com.dl.mjweb.service.imp;

import com.dl.common.model.entity.DLArea;
import com.dl.common.repository.AreaRepository;
import com.dl.mjweb.service.IAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class AreaServiceImpl implements IAreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public void delete(int id) {
        if (!areaRepository.existsById(id)){
            log.debug("DLArea doesn`t exist this id: "+id);
        }
        areaRepository.deleteById(id);
    }

    @Override
    public DLArea getById(int id) {
        if (!areaRepository.existsById(id)){
            log.debug("DLArea doesn`t exist this id: "+id);
            return null;
        }
        return areaRepository.findById(id).get();
    }

    @Override
    public Page page(Specification<DLArea> spec, Pageable pageable) {
        Page<DLArea> page = areaRepository.findAll(spec,pageable);
        return page;
    }

    @Override
    public void save(DLArea bean) {
        areaRepository.save(bean);
    }

    @Override
    public void update(DLArea bean) {
        areaRepository.save(bean);
    }

    @Override
    public List<DLArea> findAll(Specification<DLArea> spec) {
        return areaRepository.findAll(spec);
    }
}
