package com.dl.mjweb.service.imp;

import com.dl.common.model.entity.Permission;
import com.dl.common.repository.PermissionRepository;
import com.dl.mjweb.service.IPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private com.dl.common.repository.PermissionRepository PermissionRepository;

    @Override
    public void delete(int id) {
        if (!PermissionRepository.existsById(id)){
            log.debug("Permission doesn`t exists this id : "+id);
        }
        PermissionRepository.deleteById(id);
    }

    @Override
    public Permission getById(int id) {
        if (!PermissionRepository.existsById(id)){
            log.debug("Permission doesn`t exists this id : "+id);
            return null;
        }
        return PermissionRepository.findById(id).get();
    }

    @Override
    public Page page(Specification<Permission> spec, Pageable pageable) {
        Page<Permission> page = PermissionRepository.findAll(spec, pageable);
        return page;
    }

    @Override
    public void save(Permission bean) {
        PermissionRepository.save(bean);
    }

    @Override
    public void update(Permission bean) {
        PermissionRepository.save(bean);
    }

    @Override
    public List<Permission> findAll() {
        return PermissionRepository.findAll();
    }
}
