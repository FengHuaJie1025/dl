package com.dl.mjapp.service;

import com.dl.common.model.entity.DLAdverLog;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IAdverLogService {

    void save(DLAdverLog adverLog);
}
