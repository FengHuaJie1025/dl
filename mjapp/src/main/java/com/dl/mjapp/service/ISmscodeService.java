package com.dl.mjapp.service;

import com.dl.common.model.entity.DlSmscode;

public interface ISmscodeService {
    public Boolean sendVerifyCode(String phone);

    public DlSmscode generateSmscode(String phone);

    DlSmscode checkSmsCode(String phone, String code);
}
