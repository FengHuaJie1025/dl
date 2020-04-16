package com.dl.mjapp.service.imp;

import com.dl.common.model.entity.DLAdverLog;
import com.dl.common.repository.AdverLogRepository;
import com.dl.common.repository.AdverRepository;
import com.dl.mjapp.service.IAdverLogService;
import com.dl.mjapp.service.IAdverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AdverLogServiceImpl implements IAdverLogService {

    @Autowired
    private AdverLogRepository repository;

    @Override
    public void save(DLAdverLog adverLog) {
        repository.save(adverLog);
    }
}
