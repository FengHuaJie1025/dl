package com.dl.mjweb.service.imp;

import com.dl.common.model.entity.DlProject;
import com.dl.common.repository.ProjectRepository;
import com.dl.mjweb.service.IProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void delete(int id) {
        if (!projectRepository.existsById(id)){
            log.debug("Project doesn`t exist this id: "+id);
        }
        projectRepository.deleteById(id);
    }

    @Override
    public DlProject getById(int id) {
        if (!projectRepository.existsById(id)){
            log.debug("Project doesn`t exist this id: "+id);
            return null;
        }
        return projectRepository.findById(id).get();
    }

    @Override
    public Page page(Specification<DlProject> spec, Pageable pageable) {
        Page<DlProject> page = projectRepository.findAll(spec,pageable);
        return page;
    }

    @Override
    public void save(DlProject bean) {
        projectRepository.save(bean);
    }

    @Override
    public void update(DlProject bean) {
        projectRepository.save(bean);
    }

    @Override
    public List<DlProject> findAll() {
        return projectRepository.findAll();
    }
}
