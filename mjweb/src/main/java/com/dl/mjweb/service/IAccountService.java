package com.dl.mjweb.service;

import com.dl.common.model.entity.Account;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IAccountService {
    void delete(int id);

    @NonNull
    Account getById(int id);

    @NonNull
    Page page(Specification<Account> spec, Pageable pageable);

    void save(Account bean);

    void update(Account bean);

    Account checkAccount(Account bean);

    List<Account> findAll(Specification<Account> spec);

}
