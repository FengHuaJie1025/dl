package com.dl.mjweb.service.imp;

import com.dl.common.model.entity.DlUser;
import com.dl.common.repository.DlUserRepository;
import com.dl.mjweb.service.IDlUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class DlUserServiceImpl implements IDlUserService {

    @Autowired
    private DlUserRepository userRepository;

    @Override
    public void delete(int id) {
        if (!userRepository.existsById(id)){
            log.debug("DlUser doesn`t exist this id: "+id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public DlUser getById(int id) {
        if (!userRepository.existsById(id)){
            log.debug("DlUser doesn`t exist this id: "+id);
            return null;
        }
        return userRepository.findById(id).get();
    }

    @Override
    public Page page(Specification<DlUser> spec, Pageable pageable) {
        Page<DlUser> page = userRepository.findAll(spec,pageable);
        return page;
    }

    @Override
    public void save(DlUser bean) {
        userRepository.save(bean);
    }

    @Override
    public void update(DlUser bean) {
        userRepository.save(bean);
    }

    @Override
    public List<DlUser> findAll() {
        return userRepository.findAll();
    }
}
