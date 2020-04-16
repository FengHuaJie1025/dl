package com.dl.mjweb.service.imp;

import com.dl.common.model.entity.DlOperationLog;
import com.dl.common.repository.DlOperationLogRepository;
import com.dl.mjweb.service.IDlOperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class DlOperationLogServiceImpl implements IDlOperationLogService {

    @Autowired
    private com.dl.common.repository.DlOperationLogRepository DlOperationLogRepository;

    @Override
    public void delete(int id) {
        if (!DlOperationLogRepository.existsById(id)){
            log.debug("DlOperationLog doesn`t exist this id: "+id);
        }
        DlOperationLogRepository.deleteById(id);
    }

    @Override
    public DlOperationLog getById(int id) {
        if (!DlOperationLogRepository.existsById(id)){
            log.debug("DlOperationLog doesn`t exist this id: "+id);
            return null;
        }
        return DlOperationLogRepository.findById(id).get();
    }

    @Override
    public Page page(Specification<DlOperationLog> spec, Pageable pageable) {
        Page<DlOperationLog> page = DlOperationLogRepository.findAll(spec,pageable);
        return page;
    }

    @Override
    public void save(DlOperationLog bean) {
        DlOperationLogRepository.save(bean);
    }

    @Override
    public void update(DlOperationLog bean) {
        DlOperationLogRepository.save(bean);
    }
}
