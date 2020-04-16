package com.dl.mjweb.service.imp;

import com.dl.common.exception.BadRequestException;
import com.dl.common.model.entity.Account;
import com.dl.common.repository.AccountRepository;
import com.dl.mjweb.event.TestEvent;
import com.dl.mjweb.service.IAccountService;
import com.dl.mjweb.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @Override
    public void delete(int id) {
        if (!accountRepository.existsById(id)){
            log.debug("Account doesn`t exist this id: "+id);
        }
        accountRepository.deleteById(id);
    }

    @Override
    public Account getById(int id) {
        Object o = null;
        try {
            o = redisUtil.get("account-" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!Objects.isNull(o)){
            return (Account)o;
        }
        if (!accountRepository.existsById(id)){
            log.debug("Account doesn`t exist this id: "+id);
            return null;
        }
        Account account = accountRepository.findById(id).get();
        redisUtil.set("account-"+id,account,10*60);

        return account;
    }

    @Override
    public Page page(Specification<Account> spec, Pageable pageable) {
        Page<Account> page = accountRepository.findAll(spec,pageable);
        return page;
    }

    @Override
    public void save(Account bean) {
        accountRepository.save(bean);
    }

    @Override
    public void update(Account bean) {
        accountRepository.save(bean);
    }

    @Override
    public Account checkAccount(Account bean) {
        String mismatchTip = "用户名或者密码不正确";

        List<Account> accounts = accountRepository.findByLoginAccount(bean.getLoginAccount());
        if(Objects.isNull(accounts)){
            eventPublisher.publishEvent(new TestEvent(this, bean.getLoginAccount()));
            throw new BadRequestException(mismatchTip);
        }
        //需要在数据库做唯一约束！
        Account account = accounts.get(0);
        //实际上应该使用盐和MD5进行加密！
        if (!Objects.equals(bean.getPassword(),account.getPassword())){
            eventPublisher.publishEvent(new TestEvent(this, bean.getLoginAccount()));
            throw new BadRequestException(mismatchTip);
        }
        return account;
    }

    @Override
    public List<Account> findAll(Specification<Account> spec) {
        return accountRepository.findAll(spec);
    }


}
