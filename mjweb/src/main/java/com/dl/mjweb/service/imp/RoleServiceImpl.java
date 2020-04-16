package com.dl.mjweb.service.imp;

import com.dl.common.model.entity.Role;
import com.dl.common.repository.RoleRepository;
import com.dl.mjweb.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private com.dl.common.repository.RoleRepository RoleRepository;

    @Override
    public void delete(int id) {
        if (!RoleRepository.existsById(id)){
            log.debug("Role doesn`t exist this id: "+id);
        }
        RoleRepository.deleteById(id);
    }

    @Override
    public Role getById(int id) {
        if (!RoleRepository.existsById(id)){
            log.debug("Role doesn`t exist this id: "+id);
            return null;
        }
        return RoleRepository.findById(id).get();
    }

    @Override
    public Page page(Specification<Role> spec, Pageable pageable) {
        Page<Role> page = RoleRepository.findAll(spec, pageable);
        return page;
    }

    @Override
    public void save(Role bean) {
        RoleRepository.save(bean);
    }

    @Override
    public void update(Role bean) {
        RoleRepository.save(bean);
    }

    @Override
    public List<Role> findAll() {
        return RoleRepository.findAll();
    }
}
