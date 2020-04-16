package com.dl.mjapp.service.imp;

import cn.hutool.core.date.DateUtil;
import com.dl.common.model.entity.DlSmscode;
import com.dl.common.repository.AreaRepository;
import com.dl.common.repository.SmscodeRepository;
import com.dl.common.utils.DateUtils;
import com.dl.common.utils.QueryUtil;
import com.dl.common.utils.SearchFilter;
import com.dl.common.utils.smsutils.CCPRestSmsSDK;
import com.dl.mjapp.service.IOpenlogService;
import com.dl.mjapp.service.ISmscodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class SmscodeServiceImpl implements ISmscodeService {

    @Autowired
    private SmscodeRepository repository;

    @Override
    public Boolean sendVerifyCode(String phone) {
        Integer count = countSmscodeToday(phone);
        if (count >= 5){
            throw new RuntimeException("发送失败，今日发送短信超过最大次数！");
        }
        DlSmscode dlSmscode = getNewestCodeToday(phone);
        //今天没有发过短信或者今天最新的短信已经过了有效期
        if (dlSmscode == null || dlSmscode.getValueTime().before(new Date())){
            dlSmscode = generateSmscode(phone);
        }
        return sendSmscode(phone,dlSmscode.getSmsCode());
    }

    private DlSmscode getNewestCodeToday(String phone) {
        List<DlSmscode> list = getDlSmscodes(phone);
        if (list.isEmpty()){
            return null;
        }
//        单纯的排序无需使用stream！
//        Optional<DlSmscode> first = list.stream().sorted((o1, o2) -> {
//            return o1.getValueTime().before(o2.getValueTime()) ? 1:0 ;
//        }).findFirst();
//        return first.get();

        list.sort((o1, o2) -> {
            return o1.getValueTime().before(o2.getValueTime()) ? 1:0 ;
        });
        return list.get(0);
    }

    private List<DlSmscode> getDlSmscodes(String phone) {
        Collection<SearchFilter> filters = new ArrayList<>();
        filters.add(new SearchFilter("phone",SearchFilter.Operator.EQ,phone));
        filters.add(new SearchFilter("createTime",SearchFilter.Operator.GT,new Date()));
        Specification<DlSmscode> spec = QueryUtil.bySearchFilter(filters);
        return repository.findAll(spec);
    }

    private Integer countSmscodeToday(String phone) {
        List<DlSmscode> list = getDlSmscodes(phone);
        return list.size();
    }

    private Boolean sendSmscode(String phone,String smscode) {
        HashMap<String, Object> result = null;
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount("8aaf0708570871f801571d3b3b5c0b62", "1a7e78407cb1421684c4c6f58f3bb4f5");
        restAPI.setAppId("8aaf0708570871f801571d3b3b980b64");

        result = restAPI.sendTemplateSMS(phone, "118069", new String[] { smscode, "5分钟" });

        System.out.println("SDKTestGetSubAccounts result=" + result);
        if ("000000".equals(result.get("statusCode"))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DlSmscode generateSmscode(String phone) {
        //这种算法生成方式没有0开头的
        String verificationCode = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        DlSmscode smscode = new DlSmscode();
        smscode.setPhone(phone);
        smscode.setSmsCode(verificationCode);
        smscode.setValueTime(DateUtils.addMinuteDate(5));
        repository.save(smscode);

        return smscode;
    }

    @Override
    public DlSmscode checkSmsCode(String phone, String code) {
        Collection<SearchFilter> filters = new ArrayList<>();
        filters.add(new SearchFilter("phone",SearchFilter.Operator.EQ,phone));
        filters.add(new SearchFilter("smsCode",SearchFilter.Operator.EQ,code));
        filters.add(new SearchFilter("createTime",SearchFilter.Operator.GT,new Date()));
        Optional<DlSmscode> codeOptional = repository.findOne(QueryUtil.bySearchFilter(filters));
        if (!codeOptional.isPresent()){
            throw new RuntimeException("验证码不存在");
        }
        return codeOptional.get();
    }
}
