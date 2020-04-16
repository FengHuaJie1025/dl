package com.dl.mjweb.service.imp;

import com.dl.common.model.entity.DlOpenlog;
import com.dl.common.repository.OpenlogRepository;
import com.dl.mjweb.service.IOpenlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class OpenlogServiceImpl implements IOpenlogService {

    @Autowired
    private OpenlogRepository openlogRepository;

    @Override
    public void delete(int id) {
        if (!openlogRepository.existsById(id)){
            log.debug("DlOpenlog doesn`t exist this id: "+id);
        }
        openlogRepository.deleteById(id);
    }

    @Override
    public DlOpenlog getById(int id) {
        if (!openlogRepository.existsById(id)){
            log.debug("DlOpenlog doesn`t exist this id: "+id);
            return null;
        }
        return openlogRepository.findById(id).get();
    }

    @Override
    public Page page(Specification<DlOpenlog> spec, Pageable pageable) {
        Page<DlOpenlog> page = openlogRepository.findAll(spec,pageable);
        return page;
    }

    @Override
    public void save(DlOpenlog bean) {
        openlogRepository.save(bean);
    }

    @Override
    public void update(DlOpenlog bean) {
        openlogRepository.save(bean);
    }

    @Override
    public List<DlOpenlog> findAll() {
        return openlogRepository.findAll();
    }
}
