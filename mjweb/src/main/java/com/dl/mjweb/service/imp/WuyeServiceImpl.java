package com.dl.mjweb.service.imp;

import com.dl.common.model.entity.Dlwuye;
import com.dl.common.repository.WuyeRepository;
import com.dl.mjweb.service.IWuyeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class WuyeServiceImpl implements IWuyeService {

    @Autowired
    private WuyeRepository wuyeRepository;

    @Override
    public void delete(int id) {
        if (!wuyeRepository.existsById(id)){
            log.debug("Dlwuye doesn`t exist this id: "+id);
        }
        wuyeRepository.deleteById(id);
    }

    @Override
    public Dlwuye getById(int id) {
        if (!wuyeRepository.existsById(id)){
            log.debug("Dlwuye doesn`t exist this id: "+id);
            return null;
        }
        return wuyeRepository.findById(id).get();
    }

    @Override
    public Page page(Specification<Dlwuye> spec, Pageable pageable) {
        Page<Dlwuye> page = wuyeRepository.findAll(spec,pageable);
        return page;
    }

    @Override
    public void save(Dlwuye bean) {
        wuyeRepository.save(bean);
    }

    @Override
    public void update(Dlwuye bean) {
        wuyeRepository.save(bean);
    }

    @Override
    public List<Dlwuye> findAll() {
        return wuyeRepository.findAll();
    }
}
