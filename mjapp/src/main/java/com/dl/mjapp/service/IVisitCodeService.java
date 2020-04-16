package com.dl.mjapp.service;

import com.dl.common.model.entity.DlVisiteCode;

import java.util.List;

public interface IVisitCodeService {
    List<DlVisiteCode> findAll(String phone);

    DlVisiteCode generateVisitCode(DlVisiteCode code);
}
